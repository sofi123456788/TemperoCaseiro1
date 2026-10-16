package com.example.temperocaseiro1.model;

public class ExcluirContaRequest {

    private String emailExcluir;

    public ExcluirContaRequest(String emailExcluir) {
        this.emailExcluir = emailExcluir;
    }

    public String getEmailExcluir() {
        return emailExcluir;
    }

    public void setEmailExcluir(String emailExcluir) {
        this.emailExcluir = emailExcluir;
    }
}