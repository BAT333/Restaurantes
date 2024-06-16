package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.DataEmployeeCreate;
import com.atendimento.restaurantes.model.Employee.DataEmployee;
import com.atendimento.restaurantes.model.Employee.DataLoginsDTO;
import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.Employee.DataUpdateEmployoo;
import com.atendimento.restaurantes.model.order.DataOrder;
import com.atendimento.restaurantes.service.Employee.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "bearer-key")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping
    @Transactional
    @CircuitBreaker(name = "erroServers",fallbackMethod = "errorsForSomeReason")
    public ResponseEntity<DataEmployeeCreate> register(@RequestBody @Valid DataRegisterEmployee dataRester, UriComponentsBuilder builder){
        return this.service.registerEmployee(dataRester,builder);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataEmployee> update(@PathVariable Long id,@RequestBody DataUpdateEmployoo updateEmployoo){
        return this.service.updateEmployee(id,updateEmployoo);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        return service.deleteEmployee(id);
    }
    @GetMapping
    public ResponseEntity<Page<DataEmployee>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return this.service.list(pageable);
    }
    @GetMapping("{id}")
    public ResponseEntity<DataEmployee> employee(@PathVariable Long id){
        return this.service.employeeID(id);
    }
    @PutMapping("/update")
    @Transactional
    @CircuitBreaker(name = "erroServers",fallbackMethod = "errorsForSomeReason")
    public ResponseEntity<DataLoginsDTO> update(@RequestBody @Valid DataLoginsDTO dto){
        return this.service.update(dto);
    }
    private ResponseEntity<Object> errorsForSomeReason(DataOrder order, Long id, UriComponentsBuilder builder, Throwable throwable){
        return new ResponseEntity<Object>("ContactsServersDown", HttpStatus.FORBIDDEN);
    }


}
