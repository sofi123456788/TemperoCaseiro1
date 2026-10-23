
package com.example.temperocaseiro1;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class tela_ajuda extends AppCompatActivity {

    private LinearLayout btnSocorro;
    private LinearLayout btnChatAnonimo;
    private LinearLayout btnAtendimentoIA;
    private LinearLayout btnCentrosApoio;
    private LinearLayout btnDenunciaAjuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ajuda);

        btnSocorro = findViewById(R.id.btnSocorro);
        btnChatAnonimo = findViewById(R.id.btnChatAnonimo);
        btnAtendimentoIA = findViewById(R.id.btnAtendimentoIA);
        btnCentrosApoio = findViewById(R.id.btnCentrosApoio);
        btnDenunciaAjuda = findViewById(R.id.btnDenunciaAjuda);

        btnDenunciaAjuda.setOnClickListener(v -> {
            Intent intent = new Intent(
                    tela_ajuda.this,
                    tela_denuncia.class
            );
            startActivity(intent);
        });

        // As outras opções serão conectadas
        // às respectivas funcionalidades.
    }
}
