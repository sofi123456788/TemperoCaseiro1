package com.example.temperocaseiro1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;

import android.widget.Toast;

import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.ExcluirContaRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_excluir_conta extends AppCompatActivity {

    private Button btnExcluirConta;
    private Button btnCancelarExclusao;
    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_excluir_conta);
        BarrasSistema.aplicar(this);
        findViewById(R.id.btnVoltarExcluir).setOnClickListener(v -> finish());

        btnExcluirConta = findViewById(R.id.btnExcluirConta);
        btnCancelarExclusao = findViewById(R.id.btnCancelarExclusao);

        emailUsuario = getIntent().getStringExtra("emailUsuario");
        if (emailUsuario == null || emailUsuario.isEmpty()) {
            emailUsuario = com.example.temperocaseiro1.model.SessaoUsuario.getEmail();
        } // recupera o email enviado pela tela de configurações

        btnExcluirConta.setOnClickListener(v -> { // verifica se de fato o email foi enviado
            if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
                Toast.makeText(this, "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            } else {
                btnExcluirConta.setEnabled(false);
                exclusaoAPI(emailUsuario); // somente após confirmação explícita
            }

        });

        btnCancelarExclusao.setOnClickListener(v -> {
            finish();
        });


    }
    private void exclusaoAPI(String email) { // metodo que recebe o email enviado e cria um objeto requisição

        ExcluirContaRequest excluirContaRequest = new ExcluirContaRequest(email);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class);

        Call<String> call = authApi.excluir(excluirContaRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnExcluirConta.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(tela_excluir_conta.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Conta excluída com sucesso".equals(response.body())) {
                        Intent intent = new Intent(tela_excluir_conta.this, tela_conta_excluida.class);
                        com.example.temperocaseiro1.model.SessaoUsuario.encerrarSessao();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(
                            tela_excluir_conta.this,
                            "Erro ao excluir conta. Código: " + response.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                btnExcluirConta.setEnabled(true);
                Toast.makeText(tela_excluir_conta.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}