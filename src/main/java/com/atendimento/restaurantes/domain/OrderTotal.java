package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.order.DataOrder;
import com.atendimento.restaurantes.model.order.DataOrderComplete;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pedidototal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderTotal {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "ativo")
    private boolean active;
    @Column(name = "data")
    private LocalDate date = LocalDate.now();
    @OneToMany(mappedBy = "orderTotal",cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public OrderTotal(DataOrder order) {
        this.name = order.name();
        this.active =true;
    }

    public void addOrder(Order order){
        order.setOrderTotal(this);
        this.orders.add(order);
        this.total =this.total.add(order.getValuesTotal());
    }

    public void delete() {
        this.active = false;
        this.orders.forEach(Order::destivar);
    }

    public void recalculating(Order total) {
        this.total =this.total.add(total.getValuesTotal());
    }

    public void zeroValue() {
        this.total = BigDecimal.ZERO;
    }

    public List<DataOrderComplete> listOrdes() {
        return this.orders.stream().map(DataOrderComplete::new).collect(Collectors.toList());
    }
}
