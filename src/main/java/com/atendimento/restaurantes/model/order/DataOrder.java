package com.atendimento.restaurantes.model.order;

import com.atendimento.restaurantes.domain.OrderTotal;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DataOrder(
        @NotNull
        String name,
        List<Drinks> Drinks,
        List<Food> foods,
        String description,
        Long idOrderTotal


) {
}
