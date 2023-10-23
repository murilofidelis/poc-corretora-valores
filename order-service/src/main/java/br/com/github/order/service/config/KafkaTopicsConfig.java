package br.com.github.order.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic orderExecution(@Value("${spring.kafka.topics.order-execution.name}") String topic,
                             @Value("${spring.kafka.default-partitions}") Integer partitions,
                             @Value("${spring.kafka.replications}") Integer replications) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replications)
                .build();
    }

    @Bean
    public NewTopic orderDlt(@Value("${spring.kafka.topics.order-dlt.name}") String topic,
                             @Value("${spring.kafka.default-partitions}") Integer partitions,
                             @Value("${spring.kafka.replications}") Integer replications) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replications)
                .build();
    }

}
