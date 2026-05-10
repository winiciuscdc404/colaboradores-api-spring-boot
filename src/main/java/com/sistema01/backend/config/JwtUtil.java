package com.sistema01.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key chave = Keys.hmacShaKeyFor(
            "minha-chave-secreta-fixa-para-jwt-32chars!!".getBytes()
    );
    private final long EXPIRACAO = 1000L * 60 * 60 * 8;

    public String gerarToken(String nome) {
        return Jwts.builder()
                .setSubject(nome)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(chave)
                .compact();
    }

    public String gerarToken(String nome, String role) {
        return Jwts.builder()
                .setSubject(nome)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(chave)
                .compact();
    }

    public String extrairRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extrairNome(String token) {
        return getClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}