package com.atendimento.restaurantes.model.order;

import com.atendimento.restaurantes.domain.Order;
import com.atendimento.restaurantes.domain.OrderTotal;

import java.math.BigDecimal;
import java.util.List;

public record DataDamandCompleta(
        Long id,
        String name,
        BigDecimal total,
       List<DataOrderComplete> DataDamand
) {
    public DataDamandCompleta(OrderTotal orderTotal) {
        this(orderTotal.getId(), orderTotal.getName(), orderTotal.getTotal(),orderTotal.listOrdes());
    }



}
