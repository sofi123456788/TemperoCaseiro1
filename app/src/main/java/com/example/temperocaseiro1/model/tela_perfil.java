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
import com.example.temperocaseiro1.tela_login;

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

    private TextView editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);
        txtSaudacao = findViewById(R.id.txtSaudacao);
        editEmail =  findViewById(R.id.editEmail);

        String nomeCompleto = getIntent().getStringExtra("nomeUsuario");
        String email = getIntent().getStringExtra("emailUsuario");

        if (nomeCompleto != null && !nomeCompleto.trim().isEmpty()) {
            String primeiroNome = nomeCompleto.trim().split("\\s+")[0];
            txtSaudacao.setText("Olá, " + primeiroNome + "!");
        }

        if (email != null && !email.trim().isEmpty()) {
                String emailusu = email.trim().split("\\s+")[0];
                editEmail.setText(emailusu);
        }

        // Configuração das margens do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

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
            startActivity(intent);
        });

        // Sair da conta - terminar a implementação depois
        layoutSairConta.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, tela_login.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });

        // Tela inicial
        // Voltar para a tela inicial
        menuEntrar.setOnClickListener(v -> {

            Intent intent = new Intent(tela_perfil.this, MainActivity.class);

            intent.putExtra("nomeUsuario", nomeCompleto);

            intent.putExtra("emailUsuario",email);

            startActivity(intent);
        });


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
            Intent intent = new Intent(tela_perfil.this, BoasVindasActivity.class);
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
    }
}
