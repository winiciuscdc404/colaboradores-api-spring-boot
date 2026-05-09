package com.sistema01.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "COLABORADORES")
public class BackendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;

    @Column(name = "NOME")
    String nome;

    @Column(name = "SENHA")
    String senha;

    @Column(name = "SCORE")
    Integer score;

    @ManyToOne
    @JoinColumn(name = "ID_CHEFE")
    @JsonIgnoreProperties("chefe")
    BackendEntity chefe;

    public BackendEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BackendEntity getChefe() {
        return chefe;
    }

    public void setChefe(BackendEntity chefe) {
        this.chefe = chefe;
    }
}
