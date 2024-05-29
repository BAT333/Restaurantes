package com.atendimento.restaurantes.service.Drink;

import com.atendimento.restaurantes.domain.Drink;
import com.atendimento.restaurantes.model.drink.DataDrink;
import com.atendimento.restaurantes.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class DrinkService {
    @Autowired
    private DrinkRepository drinkRepository;
    public ResponseEntity<DataDrink> registerDrink(DataDrink drink, UriComponentsBuilder builder) {
        Drink drink1 =  drinkRepository.save(new Drink(drink));
        URI uri = builder.path("drink/{id}").buildAndExpand(drink1.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataDrink(drink1));
    }

    public ResponseEntity<DataDrink> updateDrink(Long id,DataDrink drink) {
        Optional<Drink> drinkOptional = drinkRepository.findById(id);
        drinkOptional.ifPresent(drinks-> drinks.update(drink));
        return drinkOptional.map(value -> ResponseEntity.ok(new DataDrink(value))).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity deleteDrink(Long id) {
        drinkRepository.findById(id).ifPresent(Drink::delete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<DataDrink> Drink(Long id) {
        Optional<Drink> drink = drinkRepository.findByIdAndActiveTrue(id);
        return drink.map(value -> ResponseEntity.ok(new DataDrink(value))).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity<Page<DataDrink>> Drinks(Pageable pageable) {
        Page<Drink> dataDrinks = drinkRepository.findByActiveTrue(pageable);
        return ResponseEntity.ok(dataDrinks.map(DataDrink::new));
    }
}
