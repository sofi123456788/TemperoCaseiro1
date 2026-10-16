
package com.example.temperocaseiro1.model;

public class LoginResponse {

    private String mensagem;
    private String token;

    public LoginResponse() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
