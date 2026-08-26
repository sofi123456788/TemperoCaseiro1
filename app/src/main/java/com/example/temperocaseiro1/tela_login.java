package com.example.temperocaseiro1;

import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView; // permite trabalhar com o encaminhamento de criar conta -> login
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.EditText;
import android.widget.Toast;
import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.LoginRequest;
import com.example.temperocaseiro1.model.tela_recuperar_conta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class tela_login extends AppCompatActivity {

    private TextView txtCriarConta;
    private EditText editEmailLogin;
    private EditText editSenhaLogin;
    private  TextView btnEntrar;
    private TextView txtEsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_login); // mostra a tela de login (o visual dela)

        //1. encontra os campos xml pelo id
        txtCriarConta = findViewById(R.id.txtCriarConta); // Ela procura no XML o elemento com este ID associado à variável txtCriarConta
        editEmailLogin = findViewById(R.id.editEmail);
        editSenhaLogin = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtEsqueceuSenha = findViewById(R.id.txtEsqueceuSenha);

        txtCriarConta.setOnClickListener(v -> { // quando o usuário clicar em "criar conta" executa o código abaixo
            Intent intent = new Intent(tela_login.this, tela_cadastro.class); // "estou na tela de login e quero ir para a de cadastro"
            startActivity(intent); // realmente abre a tela de cadastro
        });

        txtEsqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent (tela_login.this, tela_recuperar_conta.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(v -> {
            // captura o email e senha digitados nos campos
            String email = editEmailLogin.getText().toString();
            String senha = editSenhaLogin.getText().toString();
            // realiza uma verificação prévia
            if (email.trim().isEmpty()) {
                editEmailLogin.setError("Digite seu e-mail");
            } else if (senha.trim().isEmpty()) {
                editSenhaLogin.setError("Digite sua senha");
            } else {
                enviarLoginParaApi(email, senha); //
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void enviarLoginParaApi(String email, String senha) { // recebe os dados validados

        LoginRequest loginRequest = new LoginRequest(email, senha); // cria um objeto request que será enviado para a API

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class); // cria uma conexão com a API

        Call<String> call = authApi.login(loginRequest); // prepara uma chamada HTTP para o login do usuário

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) { // API respondeu
                if (response.isSuccessful()) {
                    Toast.makeText(tela_login.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Login realizado com sucesso".equals(response.body())) {
                        // próxima tela do app
                        Intent intent = new Intent(tela_login.this, tela_configuracoes.class);
                        intent.putExtra("emailUsuario", email);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(tela_login.this, "Erro no login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(tela_login.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}