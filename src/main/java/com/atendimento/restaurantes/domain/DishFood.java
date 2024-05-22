package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.food.DataFood;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Table(name = "pratoComida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DishFood {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "valor")
    private BigDecimal value;
    @Column(name = "descricao")
    private String description;
    @Column(name = "ativo")
    private Boolean active;

    public DishFood(DataFood food) {
        this.name = food.name();
        this.value = food.value();
        this.description= food.description();
        this.active = true;
    }

    public void delete() {
        this.active = false;
    }

    public void update(DataFood food) {
        if(food.name() != null){
            this.name =food.name();
        }
        if(food.value() != null){
            this.value = food.value();
        }
        if(food.description() != null){
            this.description = food.description();
        }
    }
}
