package com.example.temperocaseiro1.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.BoasVindasActivity;
import com.example.temperocaseiro1.MainActivity;
import com.example.temperocaseiro1.R;
import com.example.temperocaseiro1.ReceitasBD;
import com.example.temperocaseiro1.tela_login;

public class tela_perfil extends AppCompatActivity {
    private LinearLayout layoutConfiguracoesPerfil;
    private LinearLayout layoutSairConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);

        layoutConfiguracoesPerfil = findViewById(R.id.layoutConfiguracoesPerfil);
        layoutSairConta = findViewById(R.id.layoutSairConta);

        layoutConfiguracoesPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, tela_configuracoes1.class);
            startActivity(intent);
        });

        layoutSairConta.setOnClickListener(v -> {
            Intent intent = new Intent(tela_perfil.this, tela_login.class);
        });

        // Botão "Receitas" do rodapé
        LinearLayout menuReceitas = findViewById(R.id.menuReceitas);

        // Botão "Favoritos" do rodapé
        LinearLayout menuFavoritos = findViewById(R.id.menuFavoritos);

        // Ação do botão "Favoritos" para a troca de telas após precionar o botão por 4 segundos
        Handler handler = new Handler(Looper.getMainLooper()); // Permite agendar uma ação para que ocorra daqui a um tempo
        Runnable abrirTela = () -> { // ação executada após os 4 segundos determinados
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


// Ao clicar, abre a tela com todas as receitas do banco
        menuReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tela_perfil.this, ReceitasBD.class);
                startActivity(intent);

            }
        });
        setContentView(R.layout.activity_tela_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}