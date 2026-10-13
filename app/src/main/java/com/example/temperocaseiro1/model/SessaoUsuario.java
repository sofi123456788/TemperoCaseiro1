package com.example.temperocaseiro1.model;

public class SessaoUsuario {

    private static String token;

    public static void salvarToken(String novoToken) {
        token = novoToken;
    }

    public static String getToken() {
        return token;
    }

    public static boolean estaLogado() {
        return token != null && !token.isBlank();
    }

    public static void encerrarSessao() {
        token = null;
    }
}
