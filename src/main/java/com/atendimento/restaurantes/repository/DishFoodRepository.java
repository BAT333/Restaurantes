package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.DishFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishFoodRepository extends JpaRepository<DishFood, Long> {
    Optional< DishFood > findByIdAndActiveTrue(Long id);
}
