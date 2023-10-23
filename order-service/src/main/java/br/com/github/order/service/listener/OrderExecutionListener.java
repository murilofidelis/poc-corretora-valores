package br.com.github.order.service.listener;

import br.com.github.order.service.domain.dto.OrderExecutionDto;
import br.com.github.order.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderExecutionListener {

    private final OrderService service;

    @KafkaListener(
            topics = "${spring.kafka.topics.order-execution.name}",
            groupId = "order_execution_group",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "1")
    public void execution(@Payload ConsumerRecord<String, OrderExecutionDto> record, Acknowledgment ack) {
        log.info("Received Message, key: {}, offset: {}, partition: {} ", record.key(), record.offset(), record.partition());
        try {
            executionOrder(record.value());
        } finally {
            ack.acknowledge();
        }
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.order-execution.name}",
            groupId = "order_execution_group_secondary",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "1")
    public void execution2(@Payload ConsumerRecord<String, OrderExecutionDto> record, Acknowledgment ack) {
        try {
            log.info("Received Message GROUP:order_execution_group_secondary, offset: {}, partition: {}", record.offset(), record.partition());
        } finally {
            ack.acknowledge();
        }
    }

    private void executionOrder(OrderExecutionDto orderExecution) {
        service.execute(orderExecution);
    }
}
