package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.drink.DataDrink;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Table(name = "bebida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Drink {
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

    public Drink(DataDrink drink) {
        this.name = drink.name();
        this.value = drink.value();
        this.description = drink.description();
        this.active = true;
    }

    public void update(DataDrink drink) {
        if(drink.name() != null){
            this.name =drink.name();
        }
        if(drink.value() != null){
            this.value = drink.value();
        }
        if(drink.description() != null){
            this.description = drink.description();
        }
    }

    public void delete() {
        this.active = false;
    }
}
