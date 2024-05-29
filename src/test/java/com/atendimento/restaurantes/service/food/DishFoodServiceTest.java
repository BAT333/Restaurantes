package com.atendimento.restaurantes.service.food;

import com.atendimento.restaurantes.domain.DishFood;
import com.atendimento.restaurantes.domain.Drink;
import com.atendimento.restaurantes.model.food.DataFood;
import com.atendimento.restaurantes.repository.DishFoodRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DishFoodServiceTest {
    @InjectMocks
    private DishFoodService service;
    @Mock
    private DishFoodRepository dishFoodRepository;
    @Spy
    private UriComponentsBuilder builder;
    @Mock
    private DataFood dto;
    @Mock
    private DishFood dishFood;
    @Captor
    private ArgumentCaptor<DishFood> captorFood;

    @Test
    @DisplayName("""
            IF YOU ARE GOING TO SAVE SAVE AT THE TIME OF REGISTRATION
            """)
    void scenarioRegister01(){
        DataFood food = new DataFood("A", BigDecimal.ZERO,"A");
        DataFood foodExpectation = new DataFood("A", BigDecimal.ZERO,"A");

        Assertions.assertThrows(RuntimeException.class,()->service.registerFood(food,builder));
        then(dishFoodRepository).should().save(captorFood.capture());
        DishFood expectation =  new DishFood(foodExpectation);
        var value = captorFood.getValue();
        Assertions.assertEquals(expectation.getName(),value.getName());
        Assertions.assertEquals(expectation.getValue(),value.getValue());
        Assertions.assertEquals(expectation.getDescription(),value.getDescription());
    }

    @Test
    @DisplayName("""
                 If buider is being created right
                 """)
    void scenarioRegister02(){
        BDDMockito.given(dishFoodRepository.save(any())).willReturn(dishFood);
        Assertions.assertDoesNotThrow(()->service.registerFood(dto,builder));
    }

    @Test
    @DisplayName("""
            Testing which update is going correctly
            """)
    void updateFood01() {
        Long id = 1L;
        this.service.updateFood(id,dto);
        then(dishFoodRepository).should().findById(id);
    }
    @Test
    @DisplayName("""
            Testing which Delete is going correctly
            """)
    void deleteFood01() {
        Long id = 1L;
        this.service.deleteFood(id);
        then(dishFoodRepository).should().findById(id);
    }
    @Test
    @DisplayName("""
            Testing which get is going correctly
            """)
    void getFood01() {
        Long id = 1L;
        Assertions.assertThrows(RuntimeException.class,()->this.service.food(1L));
        then(dishFoodRepository).should().getReferenceById(id);
    }
    @Test
    @DisplayName("""
            Testing which get is going correctly
            """)
    void getFood02() {
        Assertions.assertThrows(RuntimeException.class,()->this.service.foods(any()));
        then(dishFoodRepository).should().findByActiveTrue(any());
    }

}