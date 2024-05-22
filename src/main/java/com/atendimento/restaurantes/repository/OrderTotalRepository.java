package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.Order;
import com.atendimento.restaurantes.domain.OrderTotal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderTotalRepository extends JpaRepository<OrderTotal,Long> {
    Page<OrderTotal> findAllByActiveTrue(Pageable pageable);
    List<OrderTotal> findAllByActiveTrue();

    List<OrderTotal> findAllByActiveTrueAndDate(LocalDate now);

    List<OrderTotal> findAllByActiveTrueAndDateBetween(LocalDate strat,LocalDate end);
}
