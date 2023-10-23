package br.com.github.homebroker.api.service;

import br.com.github.homebroker.api.domain.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${spring.kafka.topics.order.name}")
    private String orderTopic;

    private final KafkaTemplate<Object, Object> template;

    public void send(List<OrderDto> orders) {
        orders.forEach(order -> order.setOrderId(generateUuid()));
        sendOrder(orders);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    private void sendOrder(List<OrderDto> orders) {
        orders.forEach(this::send);
    }

    private void send(OrderDto order) {
        template.send(this.orderTopic, order.getShareCod(), order)
                .whenComplete((msg, ex) -> {
                    if (ex != null) {
                        log.error("Error send message: " + ex.getMessage());
                    } else {
                        log.info("OK!");
                    }
                });
    }
}
