package com.example.temperocaseiro1.model;

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

import com.example.temperocaseiro1.R;
import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.tela_configuracoes;
import com.example.temperocaseiro1.tela_login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_recuperar_conta extends AppCompatActivity {

    private EditText editEmailRecuperacao;
    private TextView btnConfirmarEmailRecuperacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_recuperar_conta);

        editEmailRecuperacao = findViewById(R.id.editEmailRecuperacao);
        btnConfirmarEmailRecuperacao = findViewById(R.id.btnConfirmarEmailRecuperacao);

        btnConfirmarEmailRecuperacao.setOnClickListener (v -> {
        String emailRec = editEmailRecuperacao.getText().toString();

        if(emailRec.trim().isEmpty()) {
            editEmailRecuperacao.setError("Preencha o email de recuperação");
        } else {
            enviarRecEmailParaAPI(emailRec);
        }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void enviarRecEmailParaAPI(String emailRec) {
        RecEmailRequest recEmailRequest = new RecEmailRequest(emailRec);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class); // cria uma conexão com a API

        Call<String> call = authApi.recuperar(recEmailRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) { // API respondeu
                if (response.isSuccessful()) {
                    Toast.makeText(tela_recuperar_conta.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Email enviado com sucesso".equals(response.body())) {
                        // próxima tela do app
                        Intent intent = new Intent(tela_recuperar_conta.this, tela_codigo_recuperacao.class);
                        intent.putExtra("emailRecuperacao", emailRec);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(tela_recuperar_conta.this, "Erro na recuperação", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(tela_recuperar_conta.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}