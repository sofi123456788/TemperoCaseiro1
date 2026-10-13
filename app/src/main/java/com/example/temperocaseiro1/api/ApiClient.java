package com.example.temperocaseiro1.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    // Endereço da API Spring Boot
    private static final String BASE_URL =
            "http://192.168.2.142:8080/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            // Configura o cliente HTTP com o interceptor JWT
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new TokenInterceptor())
                    .build();

            // Configura a comunicação com o Spring Boot
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
