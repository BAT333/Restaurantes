package com.atendimento.restaurantes.model;

import com.atendimento.restaurantes.domain.Employee;
import com.atendimento.restaurantes.model.Employee.DataLoginsDTO;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;

public record DataEmployeeCreate(
        Long id,
        String name,
        String City,
        String State,
        Workspace workspace,
        DataLoginsDTO loginsDTO
) {
    public DataEmployeeCreate(Employee employee, DataLoginsDTO logins) {
        this(employee.getId(), employee.getName(), employee.getAddress().getCity(),employee.getAddress().getState(),employee.getEmployee().getWorkspace(),logins);
    }
}