package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Page<Employee> findAllByActiveTrue(Pageable pageable);


    Optional<Employee> findByIdAndActiveTrue(Long id);
}
