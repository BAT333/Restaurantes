package com.atendimento.restaurantes.model.Employee;

import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataFunction(
        @NotNull
        Workspace workspaceName,
        String description,
        BigDecimal salary
) {
}
