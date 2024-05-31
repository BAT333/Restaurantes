package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.domain.User;
import com.atendimento.restaurantes.infrastructure.token.TokenService;
import com.atendimento.restaurantes.model.User.DataLogin;
import com.atendimento.restaurantes.model.User.UserUpdateDTO;
import com.atendimento.restaurantes.model.token.DataTokens;
import com.atendimento.restaurantes.repository.UserRepository;
import com.atendimento.restaurantes.service.user.ServiceUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceUser serviceUser;


    @PostMapping
    @Transactional
    public ResponseEntity<DataTokens> logIn (@RequestBody @Valid DataLogin login){
        var autheticationToken = new UsernamePasswordAuthenticationToken(login.login(),login.password());
        var autheticantion = manager.authenticate(autheticationToken);
        return ResponseEntity.ok(new DataTokens(service.generatesTokens((User) autheticantion.getPrincipal())));
    }
    @PutMapping("update")
    @Transactional
    public ResponseEntity<UserUpdateDTO> Updatepassword(@RequestBody UserUpdateDTO dtoInfos,HttpServletRequest request){
       return this.serviceUser.Update(dtoInfos,request);
    }
}
