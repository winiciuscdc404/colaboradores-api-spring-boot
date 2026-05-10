package com.sistema01.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFiltro jwtFiltro;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // rotas públicas
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/", "/index.html", "/login.html", "/css/**", "/js/**").permitAll()
                        // apenas ADMIN pode criar, editar, deletar e adicionar pontos
                        .requestMatchers(HttpMethod.POST, "/colaborador").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/colaborador/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/colaborador/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/colaborador/**").hasRole("ADMIN")
                        // qualquer autenticado pode listar
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                // adiciona o filtro JWT antes do filtro padrão de autenticação
                .addFilterBefore(jwtFiltro, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}