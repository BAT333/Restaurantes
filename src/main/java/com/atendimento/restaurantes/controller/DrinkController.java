package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.drink.DataDrink;
import com.atendimento.restaurantes.service.Drink.DrinkService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("drink")
@SecurityRequirement(name = "bearer-key")
public class DrinkController {
    @Autowired
    private DrinkService drinkService;


    @PostMapping
    @Transactional
    public ResponseEntity<DataDrink> register(@RequestBody @Valid DataDrink drink, UriComponentsBuilder builder){
        return drinkService.registerDrink(drink, builder);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataDrink> update(@PathVariable Long id, @RequestBody DataDrink drink){
        return drinkService.updateDrink(id,drink);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        return drinkService.deleteDrink(id);
    }
    @GetMapping("{id}")
    public ResponseEntity<DataDrink> drink(@PathVariable Long id){
        return drinkService.Drink(id);
    }
    @GetMapping()
    public ResponseEntity<Page<DataDrink>> drinks(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable){
        return drinkService.Drinks(pageable);
    }


}
