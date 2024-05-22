package com.atendimento.restaurantes.infrastructure.Security;

import com.atendimento.restaurantes.infrastructure.token.TokenService;
import com.atendimento.restaurantes.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SercurutyFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService service;
    @Autowired
    private UserRepository repostory;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = this.getTokenJWT(request);
        if(tokenJWT != null){
            String string = service.getSubject(tokenJWT);
            var usuario = repostory.findByLogins(string);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenJWT(HttpServletRequest request) {
        String tokenJTW =request.getHeader("Authorization");
        if(tokenJTW != null){
            return tokenJTW.replace("Bearer ","");
        }
        return null;
    }
}
