package com.atendimento.restaurantes.service.Order;

import com.atendimento.restaurantes.domain.*;
import com.atendimento.restaurantes.http.PayFeign;
import com.atendimento.restaurantes.model.RegisterDataPayDTO;
import com.atendimento.restaurantes.model.order.*;
import com.atendimento.restaurantes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private DishFoodRepository dishFoodRepository;
   /* @Autowired
    private EmployeeRepository employeeRepository;

    */
    @Autowired
    private OrderTotalRepository orderTotalRepository;
    @Autowired
    private PayFeign feign;



    public ResponseEntity<DataDamand> registerDamand(DataOrder order, Long id, UriComponentsBuilder builder) {
        OrderTotal total= orderTotalRepository.save(new OrderTotal(order));
        this.registerOrder(order,id,total);
        URI uri = builder.path("demand/{id}").buildAndExpand(total.getId()).toUri();
        this.registerPay(total);
        return ResponseEntity.created(uri).body(new DataDamand(total));
    }

    private void registerPay(OrderTotal total) {
        feign.registerPay(new RegisterDataPayDTO(total));
    }

    private void registerOrder(DataOrder order, Long id,OrderTotal total) {
        if(order.Drinks().size()>=order.foods().size()){
            this.DrinksBigger(order,id,total);
        }else{
            this.FoodBigger(order,id,total);
        }
    }

    private void FoodBigger(DataOrder order, Long id, OrderTotal total) {
        if(order.foods().size()>order.Drinks().size()){
            /*  Employee employee = employeeRepository.getReferenceById(id);

             */
            List<Order>orders = new ArrayList<>();
            Drink drinktest = drinkRepository.getReferenceById(1L);
            for(Food food:order.foods()){
                DishFood dishFood = dishFoodRepository.getReferenceById(food.idFood());
                orders.add(new Order(dishFood,food.quantityFood(),null,order,drinktest));
            }
            for(int i =0; i<order.Drinks().size();i++){
                Drink drink = drinkRepository.getReferenceById(order.Drinks().get(i).idDrink());
                orders.get(i).modifyDrinks(drink,order.Drinks().get(i));
            }
            for (Order order1 : orders) {
                total.addOrder(order1);
            }
        }
    }

    private void DrinksBigger(DataOrder order, Long id, OrderTotal total) {
        if(order.Drinks().size()>=order.foods().size()){
           /*   Employee employee = employeeRepository.getReferenceById(id);

            */
            List<Order>orders = new ArrayList<>();
            DishFood foodtest = dishFoodRepository.getReferenceById(1L);
            for(Drinks drinks:order.Drinks()){
                Drink drink = drinkRepository.getReferenceById(drinks.idDrink());
                orders.add(new Order(drink,drinks.quantityDrink(),null,order,foodtest));
            }
            for(int i =0; i<order.foods().size();i++){
                DishFood food = dishFoodRepository.getReferenceById(order.foods().get(i).idFood());
                orders.get(i).modifyFood(food,order.foods().get(i));
            }
            for (Order order1 : orders) {
                total.addOrder(order1);
            }
        }
    }

    public ResponseEntity<DataDamand> updateDamand(DataOrderUpdate update,Long id) {
        Optional<Order> order = orderRepository.findByIdAndActiveTrue(id);
        order.ifPresent(o-> o.update(update,this.drinkSearch(update.idDrink()),this.dishFoodSearch(update.idFood())));
        return this.calculationReturn(order.get().getOrderTotal());
    }

    private ResponseEntity<DataDamand> calculationReturn(OrderTotal orderTotal) {
        List<Order> orders = orderTotal.getOrders();
        orderTotal.zeroValue();
        for (Order total: orders){
            orderTotal.recalculating(total);
        }
        return ResponseEntity.ok(new DataDamand(orderTotal));
    }


    private Drink drinkSearch(Long id) {
        Optional<Drink> drink = drinkRepository.findByIdAndActiveTrue(id);
        return drink.orElse(null);
    }
    private DishFood dishFoodSearch(Long id) {
        Optional<DishFood>dishFood = dishFoodRepository.findByIdAndActiveTrue(id);
        return dishFood.orElse(null);
    }

    public ResponseEntity deleteDamand(Long id) {
        this.orderTotalRepository.findById(id).ifPresent(OrderTotal::delete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<DataDamandCompleta> demandId(Long id) {
        OrderTotal orderTotal = this.orderTotalRepository.getReferenceById(id);
        return ResponseEntity.ok(new DataDamandCompleta(orderTotal)) ;
    }

    public ResponseEntity<Page<DataDamand>> damand(Pageable pageable) {
        Page<OrderTotal>  orderTotals =this.orderTotalRepository.findAllByActiveTrue(pageable);
        return ResponseEntity.ok(orderTotals.map(DataDamand::new));
    }
}
