package com.atendimento.restaurantes.infrastructure.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private SercurutyFilter sercurutyFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf->csrf.disable())
                .sessionManagement(policy ->policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->{authorize.requestMatchers(HttpMethod.POST,"/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/employee").hasRole("BOSS");
                    authorize.requestMatchers(HttpMethod.GET,"/management").hasRole("BOSS");
                    authorize.requestMatchers(HttpMethod.GET,"/employee","/employee/**").hasRole("BOSS");
                    authorize.requestMatchers(HttpMethod.DELETE,"/employee/**").hasRole("BOSS");
                    authorize.requestMatchers(HttpMethod.PUT,"/employee/**").hasRole("BOSS");
                    authorize.requestMatchers(HttpMethod.POST,"/drink","/food","/demand/**").hasRole("EMPLOYEE");
                    authorize.requestMatchers(HttpMethod.DELETE,"/drink/**","/food/**","/demand/**").hasRole("EMPLOYEE");
                    authorize.requestMatchers(HttpMethod.PUT,"/drink/**","/food/**","/demand/**").hasRole("EMPLOYEE");
                    authorize.requestMatchers(HttpMethod.PUT,"/login/update").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.GET,"/drink","/drink/**","/food","/food/**","/demand","/demand/**").hasRole("USER");
                    authorize.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                authorize.anyRequest().authenticated();})
                .addFilterBefore(sercurutyFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
