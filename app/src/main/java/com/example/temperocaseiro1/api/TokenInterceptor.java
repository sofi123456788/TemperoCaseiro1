
package com.example.temperocaseiro1.api;

import androidx.annotation.NonNull;

import com.example.temperocaseiro1.model.SessaoUsuario;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain)
            throws IOException {

        // Recupera a requisição original
        Request requisicao = chain.request();

        // Recupera o token salvo durante o login
        String token = SessaoUsuario.getToken();

        // Prepara a requisição
        Request.Builder builder = requisicao.newBuilder();

        // Se existir um token, adiciona no cabeçalho
        if (token != null && !token.isBlank()) {

            builder.header(
                    "Authorization",
                    "Bearer " + token
            );
        }

        // Envia a requisição
        return chain.proceed(builder.build());
    }
}
