package com.atendimento.restaurantes.model.Employee;

import com.atendimento.restaurantes.domain.Address;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record DataRegisterEmployee(
        @NotNull
        String name,
        @NotNull
        String cpf,
        @Past
        LocalDate birthDate,
        @NotNull
        @Valid
        DataFunction workspace,
        @Valid
        @NotNull
        DataAddress address
) {
}
