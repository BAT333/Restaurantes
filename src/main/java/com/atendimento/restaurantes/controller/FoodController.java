package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.food.DataFood;
import com.atendimento.restaurantes.service.food.DishFoodService;
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
@RequestMapping("food")
@SecurityRequirement(name = "bearer-key")
public class FoodController {
    @Autowired
    private DishFoodService FoodService;


    @PostMapping
    @Transactional
    public ResponseEntity<DataFood> register(@RequestBody @Valid DataFood food, UriComponentsBuilder builder){
        return FoodService.registerFood(food,builder);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataFood> update(@PathVariable Long id, @RequestBody DataFood food){
        return FoodService.updateFood(id,food);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity update(@PathVariable Long id){
        return FoodService.deleteFood(id);
    }
    @GetMapping("{id}")
    public ResponseEntity<DataFood> food(@PathVariable Long id){
        return FoodService.food(id);
    }
    @GetMapping()
    public ResponseEntity<Page<DataFood>> foods(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable){
        return FoodService.foods(pageable);
    }


}
