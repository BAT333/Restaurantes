package com.atendimento.restaurantes.model.Employee;

import com.atendimento.restaurantes.domain.Employee;
import com.atendimento.restaurantes.domain.FunctionEmployee;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import jakarta.persistence.Column;

import java.util.List;

public record DataEmployee(
        Long id,
        String name,
        String City,
        String State,
        Workspace workspace
) {
    public DataEmployee(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getAddress().getCity(),employee.getAddress().getState(),employee.getEmployee().getWorkspace());
    }
}
