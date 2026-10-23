package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.LoginRequest;
import com.example.temperocaseiro1.model.LoginResponse;
import com.example.temperocaseiro1.model.SessaoUsuario;
import com.example.temperocaseiro1.model.tela_recuperar_conta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_login extends AppCompatActivity {

    private TextView txtCriarConta;
    private EditText editEmailLogin;
    private EditText editSenhaLogin;
    private TextView btnEntrar;
    private TextView txtEsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_login);

        // Localiza os componentes do XML
        txtCriarConta = findViewById(R.id.txtCriarConta);
        editEmailLogin = findViewById(R.id.editEmail);
        editSenhaLogin = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtEsqueceuSenha = findViewById(R.id.txtEsqueceuSenha);

        // Abre a tela de cadastro
        txtCriarConta.setOnClickListener(v -> {
            Intent intent = new Intent(tela_login.this, tela_cadastro.class);

            startActivity(intent);
        });

        // Abre a recuperação de conta
        txtEsqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(tela_login.this, tela_recuperar_conta.class);

            startActivity(intent);
        });

        // Realiza o login
        btnEntrar.setOnClickListener(v -> {

            String email = editEmailLogin.getText().toString().trim();

            String senha = editSenhaLogin.getText().toString();

            if (email.isEmpty()) {
                editEmailLogin.setError("Digite seu e-mail");
                editEmailLogin.requestFocus();
                return;
            }

            if (senha.trim().isEmpty()) {
                editSenhaLogin.setError("Digite sua senha");
                editSenhaLogin.requestFocus();
                return;
            }

            // Envia os dados para a API
            enviarLoginParaApi(email, senha);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

    // ENVIA LOGIN PARA A API SPRING BOOT
    private void enviarLoginParaApi(String email, String senha) {

        LoginRequest loginRequest = new LoginRequest(email, senha);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class);

        // Agora esperamos um LoginResponse
        Call<LoginResponse> call = authApi.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse resposta = response.body();

                    String mensagem = resposta.getMensagem();
                    String token = resposta.getToken();
                    String nomeCompleto = resposta.getNomeCompleto();

                    if ("Login realizado com sucesso".equals(mensagem) && token != null && !token.isBlank()) {

                        // Guarda o token na sessão do aplicativo
                        SessaoUsuario.salvarToken(token);

                        Toast.makeText(tela_login.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();

                        // Abre a próxima tela
                        Intent intent = new Intent(tela_login.this, MainActivity.class);

                        intent.putExtra("nomeUsuario", nomeCompleto);
                        intent.putExtra("emailUsuario", email);

                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(tela_login.this, "Resposta de autenticação inválida", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(tela_login.this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(tela_login.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
