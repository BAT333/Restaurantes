package com.atendimento.restaurantes.model.order;

public record DataOrderUpdate(
        Long idDrink,
        Integer quantityDrink,
        Long idFood,
        Integer quantityFood,
        String description

) {
}
