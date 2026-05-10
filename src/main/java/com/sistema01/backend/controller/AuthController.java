package com.sistema01.backend.controller;

import com.sistema01.backend.config.JwtUtil;
import com.sistema01.backend.model.BackendEntity;
import com.sistema01.backend.model.LoginRequest;
import com.sistema01.backend.model.LoginResponse;
import com.sistema01.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private BackendService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {

        List<BackendEntity> colaboradores = service.findAll();

        BackendEntity colaborador = colaboradores.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(request.getNome()))
                .findFirst()
                .orElse(null);

        if(colaborador == null) {
            return ResponseEntity.status(401).body("Colaborador nao encontrado");
        }

        if(!passwordEncoder.matches(request.getSenha(), colaborador.getSenha())) {
            return ResponseEntity.status(401).body("Senha incorreta");
        }

        String token = jwtUtil.gerarToken(colaborador.getNome(), colaborador.getRole());

        return ResponseEntity.ok(new LoginResponse(token));
    }


}
