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

public class tela_redefinir_senha extends AppCompatActivity {


    private EditText editNovaSenha;
    private EditText editConfirmarNovaSenha;

    private TextView btnConfirmarNovaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_redefinir_senha);

        editNovaSenha = findViewById(R.id.editNovaSenha);
        editConfirmarNovaSenha = findViewById(R.id.editConfirmarNovaSenha);
        btnConfirmarNovaSenha = findViewById(R.id.btnConfirmarNovaSenha);

        btnConfirmarNovaSenha.setOnClickListener(v-> {
            String senha1 = editNovaSenha.getText().toString();
            String senha2 = editConfirmarNovaSenha.getText().toString();

            if (senha1.trim().isEmpty()){
                editNovaSenha.setError("Preencha sua nova senha");
                return;
            }
            if(senha2.trim().isEmpty()) {
                editConfirmarNovaSenha.setError("Confirme sua nova senha");
                return;
            }
            if(!senha1.equals(senha2)) {
                editConfirmarNovaSenha.setError("As senhas não correspondem");
                return;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}