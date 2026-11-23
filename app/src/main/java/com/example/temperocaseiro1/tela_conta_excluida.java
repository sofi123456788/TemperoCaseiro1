package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;


public class tela_conta_excluida extends AppCompatActivity {
    private Button btnRetornarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conta_excluida);
        BarrasSistema.aplicar(this);

        btnRetornarInicio = findViewById(R.id.btnRetornarInicio);

        btnRetornarInicio.setOnClickListener(v -> {
            Intent intent = new Intent(tela_conta_excluida.this, tela_login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


    }
}