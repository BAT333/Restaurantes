package com.atendimento.restaurantes.model.order;

import com.atendimento.restaurantes.domain.OrderTotal;

import java.math.BigDecimal;

public record DataDamand(
        String name,
        BigDecimal total
) {
    public DataDamand(OrderTotal total) {
        this(total.getName(),total.getTotal());
    }
}
