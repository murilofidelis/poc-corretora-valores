package br.com.github.homebroker.api.domain.dto;

import br.com.github.homebroker.api.domain.enums.OrderType;

import java.math.BigDecimal;

public record SendDto(int userId,
                      OrderType orderType,
                      String shareCod,
                      int amountOrder,
                      BigDecimal prince,
                      BigDecimal minPrince,
                      BigDecimal maxPrince
) {
}
