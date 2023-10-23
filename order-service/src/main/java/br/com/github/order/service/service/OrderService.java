package br.com.github.order.service.service;

import br.com.github.order.service.domain.dto.OrderExecutionDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@SuppressWarnings("java:S106")
public class OrderService {
    public void execute(OrderExecutionDto orderExecution) {
        BigDecimal actualPrince = orderExecution.getShare().getLastPrice();
        BigDecimal minPrice = orderExecution.getOrder().getMinPrice();
        BigDecimal maxPrince = orderExecution.getOrder().getMaxPrince();
        System.out.println("\n=============================================================================\n");
        System.out.println("MENOR PREÇO R$ " + minPrice + " | MAIOR PREÇO R$ " + maxPrince + " | PRECÇO ATUAL R$ " + actualPrince);
        if (actualPrince.compareTo(minPrice) > 0 && actualPrince.compareTo(maxPrince) < 0) {
            System.out.println("COMPRAR AGORA: " + orderExecution.getShare().getCod() + " POR: R$ " + actualPrince);
        } else {
            System.out.println("SALVA E COMPRA DEPOIS");
        }
        System.out.println("=============================================================================");
    }
}
