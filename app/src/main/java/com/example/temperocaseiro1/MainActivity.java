package com.example.temperocaseiro1;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}