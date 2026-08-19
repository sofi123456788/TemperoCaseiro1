package com.example.temperocaseiro1;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ImageView imgBolo = findViewById(R.id.imgBoloCenoura);
        ImageView imgBrigadeiro = findViewById(R.id.imgBrigadeiro);
        ImageView imgPaoDeQueijo = findViewById(R.id.imgPaoDeQueijo);
        imgBolo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReceitaBoloCenouraActivity.class);
            startActivity(intent);
        });

        imgBrigadeiro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReceitaBrigadeiroActivity.class);
            startActivity(intent);
        });

        imgPaoDeQueijo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReceitaPaoDeQueijoActivity.class);
            startActivity(intent);
        });

        // Bloco do menu Adicionar Receita
        LinearLayout menuAdicionar = findViewById(R.id.menuAdicionar);
        menuAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarReceita.class);
            startActivity(intent);
        });

        // Categoria Bolos
        LinearLayout layoutBolos = findViewById(R.id.layoutBolos);
        layoutBolos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BolosActivity.class);
            startActivity(intent);
        });

        // Categoria Veganos
        LinearLayout layoutVeganos = findViewById(R.id.layoutVeganos);
        layoutVeganos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VeganosActivity.class);
            startActivity(intent);
        });

        // Categoria Salgados
        LinearLayout layoutSalgados = findViewById(R.id.layoutSalgados);
        layoutSalgados.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SalgadosActivity.class);
            startActivity(intent);
        });

        // Categoria Rápidas
        LinearLayout layoutRapidas = findViewById(R.id.layoutRapidas);
        layoutRapidas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RapidasActivity.class);
            startActivity(intent);
        });

        // Categoria Doces
        LinearLayout layoutDoces = findViewById(R.id.layoutDoces);
        layoutDoces.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DocesActivity.class);
            startActivity(intent);
        });

        // Botão "Receitas" do rodapé
        LinearLayout menuReceitas = findViewById(R.id.menuReceitas);

// Ao clicar, abre a tela com todas as receitas do banco
        menuReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReceitasBD.class);
                startActivity(intent);

            }
        });



    }
}