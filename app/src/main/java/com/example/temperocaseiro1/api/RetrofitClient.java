package com.example.temperocaseiro1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Classe responsável por criar a conexão com a API
public class RetrofitClient {


    // Endereço base da API.
    // Quando usamos emulador Android, localhost do computador é acessado pelo seu endereço ip
    private static final String BASE_URL = "http://10.0.0.104:8080/";


    // Instância única do Retrofit.
    private static Retrofit retrofit;



    // Método responsável por criar e retornar o Retrofit.
    public static Retrofit getRetrofitInstance(){


        // Verifica se ainda não existe uma conexão criada.
        if(retrofit == null){


            retrofit = new Retrofit.Builder() //inicia configuração

                    // Define o endereço da API.
                    .baseUrl(BASE_URL)

                    // Permite converter JSON em objetos Java automaticamente.
                    .addConverterFactory(GsonConverterFactory.create()) //A API envia e recebe informações no formato JSON.
                    //mas o java trabalha com objetos, entao faz a conversao

                    // Finaliza a criação do Retrofit.
                    .build();

        }


        // Retorna a conexão pronta.
        return retrofit;

    }


}