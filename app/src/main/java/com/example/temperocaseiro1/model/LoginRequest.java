package com.example.temperocaseiro1.model;

public class LoginRequest {

    private String emailLogin;
    private String senhaLogin;

    public LoginRequest(String emailLogin, String senhaLogin) {
        this.emailLogin = emailLogin;
        this.senhaLogin = senhaLogin;
    }
}

// usuário digita o login e a senha que serão capturados pela interface. Esses dados vão ser transformados em um objeto LoginRequest;
// esse objeto é transformado em JSON em AuthAPI e enviado para a API, onde lá será transformado em dto