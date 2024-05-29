package com.atendimento.restaurantes.repository;

import com.atendimento.restaurantes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {
    UserDetails findByLogins(String username);

    boolean existsByLogins(String cpf);
}
