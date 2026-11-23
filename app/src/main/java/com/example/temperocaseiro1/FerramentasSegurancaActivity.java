package com.example.temperocaseiro1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

/** Preferências locais das ferramentas de apoio e segurança. */
public class FerramentasSegurancaActivity extends AppCompatActivity {
    public static final String PREFS = "preferencias_seguranca";
    public static final String SAIDA = "saida_rapida";
    public static final String AVISOS = "avisos_apoio";

    private void discar(String numero) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numero)));
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_ferramentas_seguranca);
        com.example.temperocaseiro1.BarrasSistema.aplicar(this);
        findViewById(R.id.btnVoltarSeguranca).setOnClickListener(v -> finish());
        Switch saida = findViewById(R.id.switchSaidaRapida);
        Switch avisos = findViewById(R.id.switchAvisos);
        saida.setChecked(getSharedPreferences(PREFS, MODE_PRIVATE).getBoolean(SAIDA, true));
        avisos.setChecked(getSharedPreferences(PREFS, MODE_PRIVATE).getBoolean(AVISOS, true));
        saida.setOnCheckedChangeListener((b, ativo) ->
            getSharedPreferences(PREFS, MODE_PRIVATE).edit().putBoolean(SAIDA, ativo).apply());
        avisos.setOnCheckedChangeListener((b, ativo) ->
            getSharedPreferences(PREFS, MODE_PRIVATE).edit().putBoolean(AVISOS, ativo).apply());
        findViewById(R.id.btnCentralMulher).setOnClickListener(v -> discar("180"));
        findViewById(R.id.btnPolicia).setOnClickListener(v -> discar("190"));
    }
}
