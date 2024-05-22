package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findByIdAndActiveTrue(Long id);
}
