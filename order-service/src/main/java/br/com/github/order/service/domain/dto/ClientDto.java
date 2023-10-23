package br.com.github.order.service.domain.dto;

import lombok.Builder;

@Builder
public record ClientDto(Long id, String identificationDocument, String fullName) {
}
