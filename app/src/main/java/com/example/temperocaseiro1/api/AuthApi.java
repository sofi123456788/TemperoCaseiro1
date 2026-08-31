package com.example.temperocaseiro1.api;

import com.example.temperocaseiro1.model.CadastroRequest;
import com.example.temperocaseiro1.model.LoginRequest;
import com.example.temperocaseiro1.model.ExcluirContaRequest;
import com.example.temperocaseiro1.model.RecEmailRequest;
import com.example.temperocaseiro1.model.ValidarCodigoRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi { // define quais rotas existem na API

    @POST("auth/cadastro")
    Call<String> cadastrar(@Body CadastroRequest request); // envia o objeto (todas as informações fornecidas pelo usuário) já no corpo da requisição
    // retrofit transforma o objeto em JSON; JSON vai no corpo da requisição; API recebe essa requisição;

    @POST("auth/login") //envia o objeto (email e senha) já no corpo da requisição em forma de JSON; e a API recebe esse JSON; CadastroRequest na API transforma em DTO;
    Call<String> login(@Body LoginRequest request);

    @POST("auth/excluir")
    Call<String> excluir(@Body ExcluirContaRequest request);

    @POST("auth/recuperar")
    Call<String> recuperar(@Body RecEmailRequest request);

    @POST("auth/validar")
    Call <String> validar(@Body ValidarCodigoRequest request);
}