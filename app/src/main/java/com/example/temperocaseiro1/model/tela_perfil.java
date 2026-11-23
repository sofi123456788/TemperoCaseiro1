package com.example.temperocaseiro1.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.AdicionarReceita;
import com.example.temperocaseiro1.BoasVindasActivity;
import com.example.temperocaseiro1.MainActivity;
import com.example.temperocaseiro1.R;
import com.example.temperocaseiro1.ReceitasBD;
import com.example.temperocaseiro1.tela_ajuda;
import com.example.temperocaseiro1.model.SessaoUsuario;

import okhttp3.Cache;

public class tela_perfil extends AppCompatActivity {

    private LinearLayout layoutConfiguracoesPerfil;
    private LinearLayout layoutSairConta;
    private LinearLayout menuEntrar;
    private LinearLayout menuReceitas;
    private LinearLayout menuAdicionar;
    private LinearLayout menuFavoritos;
    private LinearLayout menuPerfil;
    private TextView txtSaudacao;

    private TextView txtEmailPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_perfil);
        com.example.temperocaseiro1.BarrasSistema.aplicar(this);
        txtSaudacao = findViewById(R.id.txtSaudacao);
        txtEmailPerfil = findViewById(R.id.txtEmailPerfil);

        SessaoUsuario.atualizarDadosSePresentes(
                getIntent().getStringExtra("nomeUsuario"), getIntent().getStringExtra("emailUsuario"));
        String nomeCompleto = SessaoUsuario.getNomeCompleto();
        String email = SessaoUsuario.getEmail();

        if (nomeCompleto != null && !nomeCompleto.trim().isEmpty()) {
            String primeiroNome = nomeCompleto.trim().split("\\s+")[0];
            txtSaudacao.setText("Olá, " + primeiroNome + "!");
        }

        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) txtSaudacao.setText("Olá!");
        if (email != null && !email.trim().isEmpty()) {
            txtEmailPerfil.setText(email.trim());
        }

        // Identificação dos componentes
        layoutConfiguracoesPerfil = findViewById(R.id.layoutConfiguracoesPerfil);
        layoutSairConta = findViewById(R.id.layoutSairConta);
        menuEntrar = findViewById(R.id.menuEntrar);
        menuReceitas = findViewById(R.id.menuReceitas);
        menuAdicionar = findViewById(R.id.menuAdicionar);
        menuFavoritos = findViewById(R.id.menuFavoritos);
        menuPerfil = findViewById(R.id.menuPerfil);

        // Configurações do perfil
        layoutConfiguracoesPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, tela_configuracoes1.class);
            intent.putExtra("nomeUsuario", SessaoUsuario.getNomeCompleto());
            intent.putExtra("emailUsuario", SessaoUsuario.getEmail());
            startActivity(intent);
        });

        // Encerra a autenticação local e impede retorno ao perfil pelo botão voltar.
        layoutSairConta.setOnClickListener(v -> SessaoUsuario.sairDaConta(this));

        // Centraliza o retorno à tela inicial sem perder o nome autenticado.
        menuEntrar.setOnClickListener(v -> SessaoUsuario.abrirInicio(this));


        // Receitas
        menuReceitas.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, ReceitasBD.class);
            startActivity(intent);
        });

        // Adicionar receita
        menuAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, AdicionarReceita.class);
            startActivity(intent);
        });

        // Favoritos: pressionar durante 4 segundos
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable abrirTela = () -> {
            Intent intent = new Intent(tela_perfil.this,
                    getSharedPreferences("boas_vindas", MODE_PRIVATE).getBoolean("apresentacao_concluida", false)
                            ? tela_ajuda.class : BoasVindasActivity.class);
            startActivity(intent);
        };

        menuFavoritos.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    handler.postDelayed(abrirTela, 4000);
                    return true;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler.removeCallbacks(abrirTela);
                    return true;
            }

            return false;
        });
        menuPerfil.setOnClickListener(v -> { /* Já estamos na tela de perfil. */ });
    }
}
