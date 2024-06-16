package com.atendimento.restaurantes.model;

import com.atendimento.restaurantes.domain.OrderTotal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegisterDataPayDTO(

        @NotNull
        @Positive
        BigDecimal value,
        @NotNull
        @Size(min = 2)
        String name,
        @NotNull
        @Size(max=19)
        String number,
        @NotNull
        @Size(max=7)
        String expiration,
        @NotNull
        @Size(min=3, max=3)
        String code,
        @NotNull
        Status status,
        @NotNull
        Long orderId,
        @NotNull
        Long formOfPaymentId
) {
        public RegisterDataPayDTO(OrderTotal total) {
                this(total.getTotal(),total.getName(),"123455","550","555",Status.CREATED, total.getId(), 1L);
        }
}
