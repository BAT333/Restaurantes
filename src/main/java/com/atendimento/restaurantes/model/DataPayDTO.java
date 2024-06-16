package com.atendimento.restaurantes.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataPayDTO (
        Long id,
        @NotNull
        BigDecimal value,
        @NotNull
        String name,
        @NotNull
        String number,
        @NotNull
        String expiration,
        @NotNull
        String code,
        @NotNull
        Status status,
        @NotNull
        Long orderId,
        @NotNull
        Long formOfPaymentId
){
}
