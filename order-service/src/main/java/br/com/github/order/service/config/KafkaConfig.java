package br.com.github.order.service.config;

import br.com.github.order.service.domain.dto.OrderExecutionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${spring.kafka.max-retry}")
    private Integer maxRetry;

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>(kafkaProperties.buildProducerProperties());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Object.class, false));
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = kafkaProperties.buildStreamsProperties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka_streams");
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        props.put(StreamsConfig.TOPOLOGY_OPTIMIZATION_CONFIG, StreamsConfig.OPTIMIZE);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderExecutionDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderExecutionDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1200000);
        DefaultKafkaConsumerFactory<String, OrderExecutionDto> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(OrderExecutionDto.class, false));
        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setSyncCommits(Boolean.TRUE);
        return factory;
    }

    private DefaultErrorHandler errorHandler() {
        DefaultErrorHandler handler = new DefaultErrorHandler(deadLetterPublishingRecoverer(), new FixedBackOff(3000L, this.maxRetry));
        handler.setLogLevel(KafkaException.Level.INFO);
        handler.addNotRetryableExceptions(IllegalArgumentException.class);
        handler.addNotRetryableExceptions(NullPointerException.class);
        return handler;
    }

    private DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {
        KafkaOperations<Object, Object> kafkaOperations = this.kafkaTemplate();
        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaOperations, (config, ex) -> new TopicPartition(config.topic() + ".DLT", config.partition()));
        BiFunction<ConsumerRecord<?, ?>, Exception, Headers> consumerRecordExceptionHeader = this.addCustomHeadersException();
        deadLetterPublishingRecoverer.setHeadersFunction(consumerRecordExceptionHeader);
        return deadLetterPublishingRecoverer;
    }

    private BiFunction<ConsumerRecord<?, ?>, Exception, Headers> addCustomHeadersException() {
        return (config, ex) -> {
            Headers headers = config.headers();
            if (ex.getCause() != null) {
                this.addHeaders(headers, ex);
            }
            return headers;
        };
    }

    private void addHeaders(Headers headers, Exception ex) {
        String exceptionClassName = ex.getCause().getClass().getName();
        headers.add("APP_EXCEPTION", exceptionClassName.getBytes(StandardCharsets.UTF_8));
    }

}
