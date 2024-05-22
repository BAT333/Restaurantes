package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.order.DataOrder;
import com.atendimento.restaurantes.model.order.DataOrderUpdate;
import com.atendimento.restaurantes.model.order.Drinks;
import com.atendimento.restaurantes.model.order.Food;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "funcionario")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "bebida")
    private Drink drink;
    @Column(name = "valorDrink")
    private BigDecimal valueDrink = BigDecimal.ZERO;
    @Column(name = "quantidabebida")
    private int quantityDrink = 0;
    @ManyToOne
    @JoinColumn(name = "prato")
    private DishFood dishFood;
    @Column(name = "valorComida")
    private BigDecimal valueFood = BigDecimal.ZERO;
    @Column(name = "quantidacomida")
    private int quantityFood = 0;
    @Column(name = "descricao")
    private String description;
    @Column(name = "ativo")
    private boolean active;
    @Column(name = "data")
    private LocalDate date = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "total")
    private OrderTotal orderTotal;

    public Order(Drink drink, Integer quantityDrink,Employee employee,DataOrder order,DishFood dishFood) {
        this.employee = employee;
        this.drink = drink;
        this.quantityDrink = quantityDrink;
        this.valueDrink = drink.getValue();
        this.active = true;
        this.description = order.description();
        this.dishFood = dishFood;
    }
    public Order(DishFood food, Integer quantityFood,Employee employee,DataOrder order,Drink drink) {
        this.dishFood = food;
        this.valueFood = food.getValue();
        this.quantityFood = quantityFood;
        this.active = true;
        this.employee = employee;
        this.description = order.description();
        this.drink =drink;
    }

    public BigDecimal getValuesTotal() {
        BigDecimal velueFood  = this.valueFood.multiply(new BigDecimal(this.quantityFood));
        BigDecimal veluesDrink =this.valueDrink.multiply(new BigDecimal(this.quantityDrink));
        return velueFood.add(veluesDrink);
    }

    public void destivar() {
        this.active = false;
    }

    public void update(DataOrderUpdate update, Drink drink, DishFood dishFood) {
        if(drink!=null){
            this.drink = drink;
            this.valueDrink = drink.getValue();
        }
        if(update.quantityDrink() !=null){
            this.quantityDrink = update.quantityDrink();
        }
        if(dishFood != null){
            this.dishFood = dishFood;
            this.valueFood = dishFood.getValue();
        }
        if(update.quantityFood()!=null){
            this.quantityFood=update.quantityFood();
        }
        if(update.description()!= null){
            this.description = update.description();
        }
    }
    public void modifyFood(DishFood food, Food food1) {
        this.dishFood = food;
        this.valueFood = food.getValue();
        this.quantityFood = food1.quantityFood();
        this.active = true;
    }

    public void modifyDrinks(Drink drink, Drinks drinks) {
        this.drink = drink;
        this.quantityDrink = drinks.quantityDrink();
        this.valueDrink = drink.getValue();
        this.active = true;
    }
}
