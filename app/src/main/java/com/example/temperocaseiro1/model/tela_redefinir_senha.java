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
import com.example.temperocaseiro1.model.RedefinirSenhaRequest;
import com.example.temperocaseiro1.tela_login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_redefinir_senha extends AppCompatActivity {


    private EditText editNovaSenha;
    private EditText editConfirmarNovaSenha;

    private TextView btnConfirmarNovaSenha;
    private String emailRed;
    private String codigoRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_redefinir_senha);

        editNovaSenha = findViewById(R.id.editNovaSenha);
        editConfirmarNovaSenha = findViewById(R.id.editConfirmarNovaSenha);
        btnConfirmarNovaSenha = findViewById(R.id.btnConfirmarNovaSenha);

        emailRed = getIntent().getStringExtra("emailRecuperacao");
        codigoRed = getIntent().getStringExtra("codigoRecuperacao");

        btnConfirmarNovaSenha.setOnClickListener(v-> {
            String novaSenha = editNovaSenha.getText().toString();
            String senha2 = editConfirmarNovaSenha.getText().toString();

            if (novaSenha.trim().isEmpty()){
                editNovaSenha.setError("Preencha sua nova senha");
                return;
            }
            if(senha2.trim().isEmpty()) {
                editConfirmarNovaSenha.setError("Confirme sua nova senha");
                return;
            }
            if(!novaSenha.equals(senha2)) {
                editConfirmarNovaSenha.setError("As senhas não correspondem");
                return;
            } else {
                enviarNovaSenhaParaAPI(novaSenha);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void enviarNovaSenhaParaAPI(String novaSenha){

        RedefinirSenhaRequest request =
                new RedefinirSenhaRequest(
                        emailRed,
                        codigoRed,
                        novaSenha
                );

        AuthApi authApi =
                ApiClient.getRetrofit().create(AuthApi.class);

        Call<String> call = authApi.alterar(request);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(
                            tela_redefinir_senha.this,
                            response.body(),
                            Toast.LENGTH_SHORT
                    ).show();

                    if ("Senha redefinida com sucesso".equals(response.body())) {
                        Intent intent = new Intent(
                                tela_redefinir_senha.this,
                                tela_login.class
                        );

                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(
                            tela_redefinir_senha.this,
                            "Erro na redefinição",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(
                        tela_redefinir_senha.this,
                        "Falha de conexão: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}