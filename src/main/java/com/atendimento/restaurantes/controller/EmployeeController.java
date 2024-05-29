package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.Employee.DataEmployee;
import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.Employee.DataUpdateEmployoo;
import com.atendimento.restaurantes.service.Employee.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<DataEmployee> register(@RequestBody @Valid DataRegisterEmployee dataRester, UriComponentsBuilder builder){
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
}
