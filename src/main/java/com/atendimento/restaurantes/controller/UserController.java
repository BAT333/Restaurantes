package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.domain.User;
import com.atendimento.restaurantes.infrastructure.token.TokenService;
import com.atendimento.restaurantes.model.User.DataLogin;
import com.atendimento.restaurantes.model.token.DataTokens;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DataTokens> logIn (@RequestBody @Valid DataLogin login){
        var autheticationToken = new UsernamePasswordAuthenticationToken(login.login(),login.password());
        var autheticantion = manager.authenticate(autheticationToken);
        return ResponseEntity.ok(new DataTokens(service.generatesTokens((User) autheticantion.getPrincipal())));
    }
}
