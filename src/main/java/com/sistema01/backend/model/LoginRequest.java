package com.sistema01.backend.model;

public class LoginRequest {

    private String nome;
    private String senha;

    public String getNome(){
        return nome;
    }

    public void setNome() {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha() {
        this.senha = senha;
    }
}
