package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.User.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "logins",unique = true)
    private String logins;
    @Column(name = "senhas")
    private String passwords;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    public User(String cpf, String hashedPassword) {
        this.logins = cpf;
        this.passwords = hashedPassword;
        this.roles = UserRoles.EMPLOYEE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == UserRoles.BOSS){
            return List.of(new SimpleGrantedAuthority("ROLE_BOSS"),new SimpleGrantedAuthority("ROLE_EMPLOYEE"),new SimpleGrantedAuthority("ROLE_USER"));
        }else if(this.roles == UserRoles.EMPLOYEE){
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"),new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return passwords;
    }

    @Override
    public String getUsername() {
        return logins;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateInfos(String logins,String passwords) {
        if(logins!=null){
            this.logins = logins;
        }
        if(passwords!=null){
            this.passwords = passwords;
        }


    }

}
