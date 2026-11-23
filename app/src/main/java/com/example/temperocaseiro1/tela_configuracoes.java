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
    private android.widget.TextView btnVoltarConfiguracoes;
    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes); // mostra o visual da tela de configurações

        btnAbrirExcluirConta = findViewById (R.id.btnAbrirExcluirConta);
        btnVoltarConfiguracoes = findViewById (R.id.btnVoltarConfiguracoes);
        emailUsuario = getIntent().getStringExtra("emailUsuario");
        if (emailUsuario == null || emailUsuario.isEmpty()) emailUsuario = com.example.temperocaseiro1.model.SessaoUsuario.getEmail(); // recupera o email enviado pela tela anterior

        btnVoltarConfiguracoes.setOnClickListener(v -> finish());

        btnAbrirExcluirConta.setOnClickListener(v -> {
            Intent intent = new Intent(tela_configuracoes.this, tela_excluir_conta.class);
            intent.putExtra("emailUsuario", emailUsuario); // a tela anterior envia o email logado
            startActivity(intent);
        });


        BarrasSistema.aplicar(this);
    }
}