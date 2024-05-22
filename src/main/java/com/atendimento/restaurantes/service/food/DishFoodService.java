package com.atendimento.restaurantes.service.food;

import com.atendimento.restaurantes.domain.DishFood;
import com.atendimento.restaurantes.model.food.DataFood;
import com.atendimento.restaurantes.repository.DishFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class DishFoodService {
    @Autowired
    private DishFoodRepository dishFoodRepository;
    public ResponseEntity<DataFood> registerFood(DataFood food, UriComponentsBuilder builder) {
        DishFood dishFood= dishFoodRepository.save(new DishFood(food));
        URI uri = builder.path("food/{id}").buildAndExpand(dishFood.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataFood(dishFood));
    }

    public ResponseEntity<DataFood> updateFood(Long id, DataFood food) {
        Optional<DishFood> dishFood= dishFoodRepository.findById(id);
        dishFood.ifPresent(f->f.update(food));
        return ResponseEntity.ok(new DataFood(dishFood.get()));
    }

    public ResponseEntity deleteFood(Long id) {
        dishFoodRepository.findById(id).ifPresent(DishFood::delete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<DataFood> food(Long id) {
        DishFood dishFood= dishFoodRepository.getReferenceById(id);
        return ResponseEntity.ok(new DataFood(dishFood));
    }

    public ResponseEntity<Page<DataFood>> foods(Pageable pageable) {
       Page<DataFood> foods = dishFoodRepository.findAll(pageable).map(DataFood::new);
        return ResponseEntity.ok(foods);
    }
}
