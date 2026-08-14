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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_excluir_conta); // carrega a tela de exlcluir_conta

        btnExcluirConta = findViewById(R.id.btnExcluirConta);
        btnCancelarExclusao = findViewById(R.id.btnCancelarExclusao);

        emailUsuario = getIntent().getStringExtra("emailUsuario"); // recupera o email enviado pela tela de configurações

        btnExcluirConta.setOnClickListener(v -> { // verifica se de fato o email foi enviado
            if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
                Toast.makeText(this, "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            } else {
                exclusaoAPI(emailUsuario); // caso o usuário tenha sido identificado, chamada a API
            }

        });

        btnCancelarExclusao.setOnClickListener(v -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void exclusaoAPI(String email) { // metodo que recebe o email enviado e cria um objeto requisição

        ExcluirContaRequest excluirContaRequest = new ExcluirContaRequest(email);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class);

        Call<String> call = authApi.excluir(excluirContaRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(tela_excluir_conta.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Conta excluída com sucesso".equals(response.body())) {
                        Intent intent = new Intent(tela_excluir_conta.this, tela_conta_excluida.class);
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
                Toast.makeText(tela_excluir_conta.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}