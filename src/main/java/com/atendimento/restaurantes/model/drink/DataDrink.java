package com.atendimento.restaurantes.model.drink;

import com.atendimento.restaurantes.domain.Drink;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataDrink(
        @NotNull
        String name,
        @NotNull
        BigDecimal value,
        @NotNull
        String description
) {
        public DataDrink(Drink drink1) {
                this(drink1.getName(),drink1.getValue(),drink1.getDescription());
        }
}
