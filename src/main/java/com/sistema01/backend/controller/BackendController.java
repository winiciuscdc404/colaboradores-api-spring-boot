package com.sistema01.backend.controller;

import com.sistema01.backend.service.BackendService;
import com.sistema01.backend.model.BackendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaborador")
@CrossOrigin(origins = "*")
public class BackendController {

    @Autowired
    BackendService business;

    @GetMapping
    public List<BackendEntity> getAll() {
        return business.findAll();
    }

    @GetMapping("/{id}")
    public BackendEntity get(@PathVariable(value = "id") Integer id) {
        return business.findById(id);
    }

    @PostMapping
    public List<BackendEntity> post(@RequestBody BackendEntity entity) {
        return business.saveAndListAll(entity);
    }

    @PatchMapping("/{id}/add-score")
    public BackendEntity addScore(@PathVariable Integer id, @RequestParam Integer pontos) {
        return business.adicionarPontos(id, pontos);
    }

    @DeleteMapping("/{id}")
    public List<BackendEntity> delete(@PathVariable Integer id) {
        business.delete(id);
        return business.findAll();
    }

    @PutMapping("/{id}")
    public BackendEntity update(@PathVariable Integer id, @RequestBody BackendEntity entity) {
        return business.update(id, entity);
    }
}
