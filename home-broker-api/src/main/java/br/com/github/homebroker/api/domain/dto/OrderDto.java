package br.com.github.homebroker.api.domain.dto;

import br.com.github.homebroker.api.domain.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String shareCod;
    private BigDecimal minPrice;
    private BigDecimal maxPrince;
    private BigDecimal prince;
    private OrderType orderType;
    private int quantity = 1;
    private Long clientId;
}
