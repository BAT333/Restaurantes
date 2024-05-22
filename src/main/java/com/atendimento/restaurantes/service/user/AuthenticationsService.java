package com.atendimento.restaurantes.service.user;

import com.atendimento.restaurantes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationsService implements UserDetailsService {
    @Autowired
    private UserRepository repostory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  repostory.findByLogins(username);
    }
}
