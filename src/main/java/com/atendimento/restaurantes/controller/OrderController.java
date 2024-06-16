package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.http.PayFeign;
import com.atendimento.restaurantes.model.order.DataDamand;
import com.atendimento.restaurantes.model.order.DataDamandCompleta;
import com.atendimento.restaurantes.model.order.DataOrder;
import com.atendimento.restaurantes.model.order.DataOrderUpdate;
import com.atendimento.restaurantes.service.Order.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("demand")
@SecurityRequirement(name = "bearer-key")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("{id}")
    @Transactional
    @CircuitBreaker(name = "erroServers",fallbackMethod = "errorsForSomeReason")
    public ResponseEntity<DataDamand> register(@RequestBody @Valid DataOrder order, @PathVariable Long id, UriComponentsBuilder builder){
        return orderService.registerDamand(order,id, builder);
    }
    private ResponseEntity<Object> errorsForSomeReason(DataOrder order,Long id, UriComponentsBuilder builder,Throwable throwable){
        return new ResponseEntity<Object>("ContactsServersDown", HttpStatus.FORBIDDEN);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataDamand> update(@PathVariable Long id,@RequestBody DataOrderUpdate update){
        return orderService.updateDamand(update,id);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        return orderService.deleteDamand(id);
    }
    @GetMapping("{id}")
    public ResponseEntity<DataDamandCompleta> demand(@PathVariable Long id){
        return orderService.demandId(id);
    }
    @GetMapping
    public ResponseEntity<Page<DataDamand>> demand(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable){
        return orderService.damand(pageable);
    }


}
