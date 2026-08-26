package com.example.temperocaseiro1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.90.36.173:8080/"; // essa linha informa aonde está a API para conectá-la (ID e porta)

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() { // pegue a configuração pronta da API, preparando a comunicação entre o android studio e a API

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}