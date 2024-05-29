package com.atendimento.restaurantes.service.Drink;

import com.atendimento.restaurantes.domain.Drink;
import com.atendimento.restaurantes.model.drink.DataDrink;
import com.atendimento.restaurantes.repository.DrinkRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DrinkServiceTest {
    @InjectMocks
    private DrinkService service;
    @Mock
    private DrinkRepository drinkRepository;

    @Spy
    private UriComponentsBuilder builder;
    @Captor
    private ArgumentCaptor<Drink> drink;
    @Mock
    private Drink willReturn;
    @Mock
    private DataDrink dto;
    @Spy
    private URI uri;

    @Test
    @DisplayName("If you are saving the information")
    void registerDrink(){
        dto = new DataDrink("Q",BigDecimal.ZERO,"Q");
        Assertions.assertThrows(RuntimeException.class,()-> service.registerDrink(dto,builder));
        then(drinkRepository).should().save(drink.capture());
        Drink drink1 = new Drink(dto);
        Assertions.assertEquals(new DataDrink("Q",BigDecimal.ZERO,"Q"),dto);
        Assertions.assertEquals(drink1,drink.getValue());
    }
    @Test
    @DisplayName("""
            Here you can see that the creation of the url passes without error
            """)
    void registerDrink02(){
        given(drinkRepository.save(new Drink())).willReturn(willReturn);
        uri = builder.path("drink/1").build().toUri();
        Assertions.assertDoesNotThrow(()-> service.registerDrink(dto,builder));
    }

    @Test
    @DisplayName("""
            Search if this is happening by the active search method by id
            """)
    void drink(){
        Long id = 1L;
        service.Drink(id);
        then(drinkRepository).should().findByIdAndActiveTrue(any());
    }
    @Test
    @DisplayName("Information update ")
    void updateDrink(){
        Long id = 1L;
        service.updateDrink(id,dto);
        then(drinkRepository).should().findById(any());
    }

    @Test
    @DisplayName("Delete information")
    void deleteDrink(){
        Long id = 1L;
        service.deleteDrink(id);
        then(drinkRepository).should().findById(any());
    }
    @Test
    @DisplayName("If you are successful, return a list of information")
    void drinks(){
        Assertions.assertThrows(RuntimeException.class,()->service.Drinks(any()));
        then(drinkRepository).should().findByActiveTrue(any());
    }


}