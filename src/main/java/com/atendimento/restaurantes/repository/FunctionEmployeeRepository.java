package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.FunctionEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionEmployeeRepository extends JpaRepository<FunctionEmployee,Long> {
}
