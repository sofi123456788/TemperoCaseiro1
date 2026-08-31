package com.example.temperocaseiro1.model;

public class RedefinirSenhaRequest {
    private String emailRed;
    private String codigoRed;
    private String novaSenha;

    public RedefinirSenhaRequest(String emailRed, String codigoRed, String novaSenha) {
        this.emailRed = emailRed;
        this.codigoRed = codigoRed;
        this.novaSenha = novaSenha;
    }

    public String getEmailRed() {

        return emailRed;
    }
    public void setEmailRed(String emailRed ) {

        this.emailRed = emailRed;
    }
    public String getCodigoRed() {
        return codigoRed;
    }

    public void setCodigoRed(String codigoRed) {
        this.codigoRed = codigoRed;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
