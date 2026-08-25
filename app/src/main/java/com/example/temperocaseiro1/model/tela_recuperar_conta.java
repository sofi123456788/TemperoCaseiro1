package com.example.temperocaseiro1.model;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.R;

public class tela_recuperar_conta extends AppCompatActivity {

    private EditText editEmailRecuperacao;
    private TextView btnConfirmarEmailRecuperacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_recuperar_conta);

        editEmailRecuperacao = findViewById(R.id.editEmailRecuperacao);
        btnConfirmarEmailRecuperacao = findViewById(R.id.btnConfirmarEmailRecuperacao);

        btnConfirmarEmailRecuperacao.setOnClickListener (v -> {
        String emailRec = editEmailRecuperacao.getText().toString();

        if(emailRec.trim().isEmpty()) {
            editEmailRecuperacao.setError("Preencha o email de recuperação");
        }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}