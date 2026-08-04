package com.example.temperocaseiro1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;

public class tela_configuracoes extends AppCompatActivity {

    private Button btnAbrirExcluirConta;
    private Button btnVoltarConfiguracoes;
    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_configuracoes); // mostra o visual da tela de configurações

        btnAbrirExcluirConta = findViewById (R.id.btnAbrirExcluirConta);
        btnVoltarConfiguracoes = findViewById (R.id.btnVoltarConfiguracoes);
        emailUsuario = getIntent().getStringExtra("emailUsuario");

        btnVoltarConfiguracoes.setOnClickListener(v -> {
            finish(); // fecha a tela atual e volta para a anterior
        });

        btnAbrirExcluirConta.setOnClickListener(v -> {
            Intent intent = new Intent(tela_configuracoes.this, tela_excluir_conta.class);
            intent.putExtra("emailUsuario", emailUsuario);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}