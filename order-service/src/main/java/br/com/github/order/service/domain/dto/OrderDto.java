package br.com.github.order.service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto implements Serializable {

    private String orderId;
    private String shareCod;
    private BigDecimal minPrice;
    private BigDecimal maxPrince;
    private BigDecimal prince;
    private String orderType;
    private int quantity;
    private Long clientId;

    @Override
    public String toString() {
        return "OrderDto{" +
               "orderId='" + orderId + '\'' +
               ", minPrice=" + minPrice +
               ", maxPrince=" + maxPrince +
               ", orderType='" + orderType + '\'' +
               ", quantity=" + quantity +
               '}';
    }
}
