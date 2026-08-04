package com.example.temperocaseiro1.model;

public class CadastroRequest {

    private String nomeCompleto;
    private String email;
    private String senha;
    private String genero;

    public CadastroRequest(String nomeCompleto, String email, String senha, String genero) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.genero = genero;
    }
}

// usuário digita todos os dados; CadastroRequest forma um objeto com eles; Esse objeto é transformado em JSON em AuthAPI, pois as informações serão
// ... enviadas nesse formato para a API; quando elas chegarem na API, serão transformadas em dto.