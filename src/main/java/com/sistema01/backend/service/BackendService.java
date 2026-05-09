package com.sistema01.backend.service;

import com.sistema01.backend.model.BackendEntity;
import com.sistema01.backend.repository.BackendRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class BackendService {
    @Autowired
    BackendRepository repository;

    public BackendEntity findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado com id"));
    }

    public void delete(Integer id) {
        BackendEntity colaborador = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado com id: " + id));
        repository.delete(colaborador);
    }

    public BackendEntity update(Integer id, BackendEntity entityAtualizada) {
        BackendEntity colaborador = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado com id: " + id));

        colaborador.setNome(entityAtualizada.getNome());

        if (entityAtualizada.getSenha() != null && !entityAtualizada.getSenha().isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(entityAtualizada.getSenha());
            colaborador.setSenha(senhaCriptografada);
        }

        if (entityAtualizada.getChefe() != null) {
            colaborador.setChefe(entityAtualizada.getChefe());
        }
        return repository.save(colaborador);
    }

    public List<BackendEntity> saveAndListAll(BackendEntity entity) {
        String senhaCriptografada = passwordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaCriptografada);
        repository.save(entity);
        return repository.findAll();
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public BackendEntity save(BackendEntity entity) {
        String senhaCriptografada = passwordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaCriptografada);

        return repository.save(entity);
    }

    public List<BackendEntity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public BackendEntity adicionarPontos(Integer id, Integer pontosNovos) {
        BackendEntity colaborador = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));

        int scoreAtual = (colaborador.getScore() != null) ? colaborador.getScore() : 0;
        colaborador.setScore(scoreAtual + pontosNovos);

        if (colaborador.getChefe() != null) {
            BackendEntity chefe = colaborador.getChefe();
            int bônus = (int) (pontosNovos * 0.10);
            int scoreChefe = (chefe.getScore() != null) ? chefe.getScore() : 0;
            chefe.setScore(scoreChefe + bônus);

            repository.save(chefe);
        }

        return repository.save(colaborador);
    }
}
