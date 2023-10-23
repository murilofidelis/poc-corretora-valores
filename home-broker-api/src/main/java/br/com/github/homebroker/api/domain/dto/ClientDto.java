package br.com.github.homebroker.api.domain.dto;

import lombok.Builder;

@Builder
public record ClientDto(Long id, String identificationDocument, String fullName) {
}
