package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BoasVindasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boas_vindas);

        setTitle("Favoritos");

        Button btnEntendi = findViewById(R.id.btnEntendi);

        btnEntendi.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BoasVindasActivity.this,
                    CentrosApoioActivity.class
            );

            startActivity(intent);
        });
    }
}