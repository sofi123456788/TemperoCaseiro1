package com.example.temperocaseiro1.model;

import android.app.Activity;
import android.content.Intent;
import com.example.temperocaseiro1.MainActivity;
import com.example.temperocaseiro1.tela_login;

/**
 * Dados da sessão autenticada durante a execução do aplicativo.
 * Não persiste o token JWT em arquivos nem em preferências sem criptografia.
 */
public final class SessaoUsuario {
    private static String token;
    private static String nomeCompleto;
    private static String email;

    private SessaoUsuario() { }

    public static void salvarToken(String novoToken) { token = novoToken; }
    public static String getToken() { return token; }
    public static boolean estaLogado() { return token != null && !token.isBlank(); }

    public static void salvarDados(String nome, String emailUsuario) {
        nomeCompleto = nome;
        email = emailUsuario;
    }

    public static String getNomeCompleto() { return nomeCompleto; }
    public static String getEmail() { return email; }

    public static void atualizarDadosSePresentes(String nome, String emailUsuario) {
        if (nome != null && !nome.trim().isEmpty()) nomeCompleto = nome;
        if (emailUsuario != null && !emailUsuario.trim().isEmpty()) email = emailUsuario;
    }

    /** Todas as rotas para Início usam o mesmo nome do usuário autenticado. */
    public static void abrirInicio(Activity origem) {
        Intent intent = new Intent(origem, MainActivity.class);
        intent.putExtra("nomeUsuario", nomeCompleto);
        intent.putExtra("emailUsuario", email);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        origem.startActivity(intent);
    }

    public static void sairDaConta(Activity origem) {
        encerrarSessao();
        Intent intent = new Intent(origem, tela_login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        origem.startActivity(intent);
        origem.finish();
    }

    public static void encerrarSessao() {
        token = null;
        nomeCompleto = null;
        email = null;
    }
}
