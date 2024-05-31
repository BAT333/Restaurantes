package com.atendimento.restaurantes.service.user;

import com.atendimento.restaurantes.domain.User;
import com.atendimento.restaurantes.infrastructure.token.TokenService;
import com.atendimento.restaurantes.model.User.UserUpdateDTO;
import com.atendimento.restaurantes.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {
    @Autowired
    private TokenService service;
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<UserUpdateDTO> Update(UserUpdateDTO dtoInfos, HttpServletRequest request) {
        User userUpdate = this.AuthenticatingUser(request);
        userUpdate.updateInfos(dtoInfos.User(),this.hashedPassword(dtoInfos.password()));
        return ResponseEntity.ok(new UserUpdateDTO(userUpdate.getLogins(),userUpdate.getPassword()));
    }

    private String hashedPassword(String password) {
        if(password!=null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(password);
        }
        return null;
    }

    private User AuthenticatingUser(HttpServletRequest request) {
        var user = userRepository.findByLogins(this.getUserLogin(request));
        var autheticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        return (User) autheticationToken.getPrincipal();
    }

    private String getUserLogin(HttpServletRequest request) {
        String tokenJWT = request.getHeader("Authorization");
        return service.getSubject(tokenJWT.replace("Bearer ",""));
    }
}
