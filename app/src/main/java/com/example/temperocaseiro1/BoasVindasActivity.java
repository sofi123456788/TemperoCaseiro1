package com.example.temperocaseiro1;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/** Apresentação exibida uma única vez; preferências locais não são enviadas à API. */
public class BoasVindasActivity extends AppCompatActivity {
    private static final String PREFS = "boas_vindas";
    private static final String JA_VIU = "apresentacao_concluida";

    private void abrir(Class<?> destino) {
        startActivity(new Intent(this, destino));
    }

    @Override protected void onCreate(Bundle estado) {
        super.onCreate(estado);

        if (getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(JA_VIU, false)) {
            abrir(tela_ajuda.class);
            finish();
            return;
        }

        setContentView(R.layout.activity_boas_vindas);
        findViewById(R.id.btnChat).setOnClickListener(v -> abrir(ChatAnonimoActivity.class));
        findViewById(R.id.btnIA).setOnClickListener(v -> abrir(AtendimentoIAActivity.class));
        findViewById(R.id.btnCentros).setOnClickListener(v -> abrir(CentrosApoioActivity.class));
        findViewById(R.id.btnEmergencia).setOnClickListener(v -> abrir(FerramentasSegurancaActivity.class));

        findViewById(R.id.btnEntendi).setOnClickListener(v -> {
            getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(JA_VIU, true).apply();
            abrir(tela_ajuda.class);
            finish();
        });
    }
}
