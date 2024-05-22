package com.atendimento.restaurantes.model.order;

import com.atendimento.restaurantes.domain.Order;

public record DataOrderComplete(
        Long id,
        Long idDrink,
        Integer quantityDrink,
        Long idFood,
        Integer quantityFood,
        String description

) {
    public DataOrderComplete(Order order){
        this(order.getId(),order.getDrink().getId(),order.getQuantityDrink(),order.getDishFood().getId(), order.getQuantityFood(), order.getDescription());
    }
}
