package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.Drink;
import com.atendimento.restaurantes.model.drink.DataDrink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink,Long> {
    Optional<Drink> findByIdAndActiveTrue(Long id);

    Page<Drink> findByActiveTrue(Pageable pageable);
}
