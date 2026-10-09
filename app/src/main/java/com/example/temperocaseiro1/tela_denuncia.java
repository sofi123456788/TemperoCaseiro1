package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.DenuciasRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_denuncia extends AppCompatActivity {

    private String tipoViolencia = "";
    private LinearLayout layoutFisica;
    private LinearLayout layoutPsicologica;
    private LinearLayout layoutSexual;
    private LinearLayout layoutPatrimonial;
    private LinearLayout layoutAmeaca;
    private LinearLayout layoutOutra;
    private Button btnEnviarDenuncia;
    private EditText editRelato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_denuncia);

        layoutFisica = findViewById(R.id.layoutFisica);
        layoutPsicologica = findViewById(R.id.layoutPsicologica);
        layoutSexual = findViewById(R.id.layoutSexual);
        layoutPatrimonial = findViewById(R.id.layoutPatrimonial);
        layoutAmeaca = findViewById(R.id.layoutAmeaca);
        layoutOutra = findViewById(R.id.layoutOutra);
        btnEnviarDenuncia = findViewById(R.id.btnEnviarDenuncia);
        editRelato = findViewById(R.id.editRelato);

        layoutFisica.setOnClickListener(v -> {
            tipoViolencia = "fisica";
        });

        layoutPsicologica.setOnClickListener(v -> {
            tipoViolencia = "psicologica";
        });

        layoutSexual.setOnClickListener(v -> {
            tipoViolencia = "sexual";
        });

        layoutPatrimonial.setOnClickListener(v -> {
            tipoViolencia = "patrimonial";
        });

        layoutAmeaca.setOnClickListener(v -> {
            tipoViolencia = "ameaca";
        });

        layoutOutra.setOnClickListener(v -> {
            tipoViolencia = "outra";
        });

        // verificação do tipo de seleção
        btnEnviarDenuncia.setOnClickListener(v -> {

            // 1. Verificar se algum tipo de violência foi selecionado
            if (tipoViolencia.isEmpty()) {
                btnEnviarDenuncia.setError("Selecione um tipo de violência");
                return;

            }
            // 2. Pega o relato do usuário e transforma em String
            String relatoViolencia = editRelato.getText().toString().trim();

            if (relatoViolencia.isEmpty()) {
                editRelato.setError("Descreva o que ocorreu");
                editRelato.requestFocus();
                return;
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void enviarDenunciaParaAPI(String tipoViolenciaSelecionada, String relatoViolencia) {

        DenuciasRequest denunciasRequest = new DenuciasRequest(tipoViolencia, relatoViolencia);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class); // cria uma conexão com a API

        Call<String> call = authApi.denuncia(denunciasRequest);// prepara uma chamada HTTP para o login do usuário

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) { // API respondeu
                if (response.isSuccessful()) {
                    Toast.makeText(tela_denuncia.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Denúncia salva com sucesso".equals(response.body())) { // abrir a próxima tela do app
                        // próxima tela do app
                        //Intent intent = new Intent(tela_login.this, tela_configuracoes.class);
                        //intent.putExtra("emailUsuario", email);
                        //startActivity(intent);
                        //finish();
                    }

                } else {
                    Toast.makeText(tela_denuncia.this, "Erro no login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(tela_denuncia.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}