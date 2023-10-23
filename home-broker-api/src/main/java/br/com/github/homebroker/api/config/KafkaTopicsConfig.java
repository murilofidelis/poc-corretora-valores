package br.com.github.homebroker.api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic orderTopic(@Value("${spring.kafka.topics.order.name}") String topic,
                               @Value("${spring.kafka.default-partitions}") Integer partitions,
                               @Value("${spring.kafka.replications}") Integer replications) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replications)
                .configs(Map.of(
                        "retention.ms", "432000000",
                        "min.insync.replicas", "2",
                        "compression.type", "zstd",
                        "segment.bytes", "50000000",
                        "segment.ms", "86400000"))
                .build();
    }

    @Bean
    public NewTopic shareTopic(@Value("${spring.kafka.topics.share.name}") String topic,
                               @Value("${spring.kafka.default-partitions}") Integer partitions,
                               @Value("${spring.kafka.replications}") Integer replications) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replications)
                .configs(Map.of("cleanup.policy", "compact",
                        "delete.retention.ms", "300000"))
                .build();
    }

}
