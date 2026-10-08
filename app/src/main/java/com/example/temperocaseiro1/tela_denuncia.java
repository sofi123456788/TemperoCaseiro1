package com.example.temperocaseiro1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;

public class tela_denuncia extends AppCompatActivity {

    private String tipoViolenciaSelecionada = "";
    private LinearLayout layoutFisica;
    private LinearLayout layoutPsicologica;
    private LinearLayout layoutSexual;
    private LinearLayout layoutPatrimonial;
    private LinearLayout layoutAmeaca;
    private LinearLayout layoutOutra;
    private Button btnEnviarDenuncia;
    private EditText editRelato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_denuncia);

        layoutFisica = findViewById(R.id.layoutFisica);
        layoutPsicologica = findViewById(R.id.layoutPsicologica);
        layoutSexual = findViewById(R.id.layoutSexual);
        layoutPatrimonial = findViewById(R.id.layoutPatrimonial);
        layoutAmeaca = findViewById(R.id.layoutAmeaca);
        layoutOutra = findViewById(R.id.layoutOutra);
        btnEnviarDenuncia = findViewById(R.id.btnEnviarDenuncia);
        editRelato = findViewById(R.id.editRelato);

        layoutFisica.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "fisica";
        });

        layoutPsicologica.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "psicologica";
        });

        layoutSexual.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "sexual";
        });

        layoutPatrimonial.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "patrimonial";
        });

        layoutAmeaca.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "ameaca";
        });

        layoutOutra.setOnClickListener(v -> {
            tipoViolenciaSelecionada = "outra";
        });

        // verificação do tipo de seleção
        btnEnviarDenuncia.setOnClickListener(v -> {

            // 1. Verificar se algum tipo de violência foi selecionado
            if (tipoViolenciaSelecionada.isEmpty()) {
                btnEnviarDenuncia.setError("Selecione um tipo de violência");
                return;

            }
            // 2. Pega o relato do usuário e transforma em String
            String relato = editRelato.getText().toString().trim();

            if (relato.isEmpty()) {
                editRelato.setError("Descreva o que ocorreu");
                editRelato.requestFocus();
                return;
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void enviarDenunciaParaAPI(String tipoViolenciaSelecionada, String relato) {


    }
}