package com.example.temperocaseiro1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        // Encontra o WebView do mapa
        WebView webView = findViewById(R.id.webViewMapa);

        // Permite que o WebView execute JavaScript
        WebSettings configuracao = webView.getSettings();
        configuracao.setJavaScriptEnabled(true);

        // Descobre qual restaurante foi selecionado
        String restaurante = getIntent().getStringExtra("restaurante");

        if (restaurante == null) {
            restaurante = "todos";
        }

        // Abre o mapa passando a informação do restaurante
        String url = "file:///android_asset/mapa.html?restaurante="
                + restaurante;

        webView.loadUrl(url);
    }

}
