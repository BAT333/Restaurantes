package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.Management.DataManagement;
import com.atendimento.restaurantes.model.Management.DataReceivedManagement;
import com.atendimento.restaurantes.service.management.ManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@SecurityRequirement(name = "bearer-key")
public class ManagementController {
    @Autowired
    private ManagementService service;

    @GetMapping
    public ResponseEntity<DataManagement> management(@RequestBody DataReceivedManagement receivedManagement){
        return service.dataManagement(receivedManagement);
    }


}
