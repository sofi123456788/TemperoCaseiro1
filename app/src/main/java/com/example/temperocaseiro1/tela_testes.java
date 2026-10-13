package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.model.SessaoUsuario;

public class tela_testes extends AppCompatActivity {

    private Button btnTestarDenuncia;
    private Button btnTestarExclusao;
    private Button btnSairTestes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_testes);

        // Localiza os botões do XML
        btnTestarDenuncia = findViewById(R.id.btnTestarDenuncia);
        btnTestarExclusao = findViewById(R.id.btnTestarExclusao);
        btnSairTestes = findViewById(R.id.btnSairTestes);

        // Verifica se o usuário está logado
        if (!SessaoUsuario.estaLogado()) {
            voltarParaLogin();
            return;
        }

        // Abre a tela de denúncia
        btnTestarDenuncia.setOnClickListener(v -> {
            Intent intent = new Intent(tela_testes.this, tela_denuncia.class);
            startActivity(intent);
        });

        // Abre a tela de configurações
        btnTestarExclusao.setOnClickListener(v -> {
            Intent intent = new Intent(tela_testes.this, tela_configuracoes.class);
            startActivity(intent);
        });

        // Encerra a sessão do usuário
        btnSairTestes.setOnClickListener(v -> {
            SessaoUsuario.encerrarSessao();
            voltarParaLogin();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

    private void voltarParaLogin() {
        Intent intent = new Intent(tela_testes.this, tela_login.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
