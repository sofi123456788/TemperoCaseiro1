package com.example.temperocaseiro1;

import android.os.Bundle;
import android.widget.TextView; // permite trabalhar com o encaminhamento de criar conta -> login
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class tela_login extends AppCompatActivity {

    private TextView txtCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_login); // mostra a tela de login (o visual dela)

        txtCriarConta = findViewById(R.id.txtCriarConta); // Ela procura no XML o elemento com este ID associado à variável txtCriarConta

        txtCriarConta.setOnClickListener(v -> { // quando o usuário clicar em "criar conta" executa o código abaixo
            Intent intent = new Intent(tela_login.this, tela_cadastro.class); // "estou na tela de login e quero ir para a de cadastro"
            startActivity(intent); // realmente abre a tela de cadastro
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}