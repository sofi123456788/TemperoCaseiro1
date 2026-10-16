package com.example.temperocaseiro1.api;

import com.example.temperocaseiro1.model.Receita;
import com.example.temperocaseiro1.model.CentroApoio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


// Interface responsável por definir as chamadas da API
public interface ApiService {

    // Envia uma nova receita para a API.
    @POST("receitas")
    Call<Receita> adicionarReceita(
            @Body Receita receita
    );

    // Busca todas as receitas cadastradas no banco.
    @GET("receitas")
    Call<List<Receita>> listarReceitas();

    // Busca todos os centros de apoio cadastrados no banco.
    @GET("centrosapoio")
    Call<List<CentroApoio>> listarCentrosApoio();

}