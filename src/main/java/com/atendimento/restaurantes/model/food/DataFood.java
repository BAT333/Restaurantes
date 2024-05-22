package com.atendimento.restaurantes.model.food;

import com.atendimento.restaurantes.domain.DishFood;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataFood(
        @NotNull
        String name,
        @NotNull
        BigDecimal value,
        @NotNull
        String description

) {
        public DataFood(DishFood dishFood) {
                this(dishFood.getName(),dishFood.getValue(), dishFood.getDescription());
        }
}
