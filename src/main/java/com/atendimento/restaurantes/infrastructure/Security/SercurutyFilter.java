package com.atendimento.restaurantes.infrastructure.Security;

import com.atendimento.restaurantes.http.LoginFeign;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SercurutyFilter extends OncePerRequestFilter {
    @Autowired
    private LoginFeign feign;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = this.getTokenJWT(request);
        var listROles = this.feign.crede(tokenJWT);
        if(listROles.isEmpty())throw new RuntimeException("USER SEM PERMISSON");
        var authentication = new UsernamePasswordAuthenticationToken(null,null,this.getRoles(listROles));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request,response);
    }

    private Collection<? extends GrantedAuthority> getRoles(List<String> listROles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(String list: listROles){
            authorities.add(new SimpleGrantedAuthority(list));
        }
        return authorities;
    }

    private String getTokenJWT(HttpServletRequest request) {
        String tokenJTW =request.getHeader("Authorization");
        if(tokenJTW != null){
            return tokenJTW.replace("Bearer ","");
        }
        throw new RuntimeException("ERRO TOKEN");
    }
}
