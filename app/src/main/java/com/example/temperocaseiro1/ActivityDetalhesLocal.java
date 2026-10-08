
        package com.example.temperocaseiro1;

import android.os.Bundle;
import android.widget.TextView;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDetalhesLocal extends AppCompatActivity {

    private TextView txtNomeLocal;
    private TextView btnVoltar;
    private TextView txtCidadeEstado;
    private TextView txtEndereco;
    private TextView txtCidadeEndereco;
    private TextView txtTelefone;
    private TextView txtFuncionamento;
    private TextView txtCategoria;
    private WebView webViewMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhes_local);

        // Campos da tela
        txtNomeLocal = findViewById(R.id.txtNomeLocal);
        btnVoltar = findViewById(R.id.btnVoltar);
        txtCidadeEstado = findViewById(R.id.txtCidadeEstado);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtCidadeEndereco = findViewById(R.id.txtCidadeEndereco);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtFuncionamento = findViewById(R.id.txtFuncionamento);
        txtCategoria = findViewById(R.id.txtCategoria);
        webViewMapa = findViewById(R.id.webViewMapa);

        WebSettings configuracao = webViewMapa.getSettings();
        configuracao.setJavaScriptEnabled(true);

        // Recebe o nome enviado pela tela anterior
        String nome = getIntent().getStringExtra("nome");
        String telefone = getIntent().getStringExtra("telefone");
        String rua = getIntent().getStringExtra("rua");
        String logradouro = getIntent().getStringExtra("logradouro");
        String bairro = getIntent().getStringExtra("bairro");
        String cidade = getIntent().getStringExtra("cidade");
        String estado = getIntent().getStringExtra("estado");
        String horarioAbertura = getIntent().getStringExtra("horarioAbertura");
        String horarioFechamento = getIntent().getStringExtra("horarioFechamento");
        String tipo = getIntent().getStringExtra("tipo");
        String enderecoMapa = "";

        if (logradouro != null && !logradouro.isEmpty()) {
            enderecoMapa += logradouro;
        } else if (rua != null && !rua.isEmpty()) {
            enderecoMapa += rua;
        }

        if (bairro != null && !bairro.isEmpty()) {
            enderecoMapa += ", " + bairro;
        }

        if (cidade != null && !cidade.isEmpty()) {
            enderecoMapa += ", " + cidade;
        }

        if (estado != null && !estado.isEmpty()) {
            enderecoMapa += ", " + estado;
        }

        String urlMapa = "file:///android_asset/mapa_centro.html?endereco="
                + android.net.Uri.encode(enderecoMapa);

        webViewMapa.loadUrl(urlMapa);

        if (nome != null && !nome.isEmpty()) {
            txtNomeLocal.setText(nome);
        }

        if (cidade != null && estado != null) {
            txtCidadeEstado.setText(cidade + " • " + estado);
        }

        String endereco = "";

        if (logradouro != null && !logradouro.isEmpty()) {
            endereco = logradouro;
        } else if (rua != null && !rua.isEmpty()) {
            endereco = rua;
        }

        if (bairro != null && !bairro.isEmpty()) {
            if (!endereco.isEmpty()) {
                endereco += " - ";
            }

            endereco += bairro;
        }

        txtEndereco.setText(endereco);

        if (cidade != null && estado != null) {
            txtCidadeEndereco.setText(cidade + " - " + estado);
        }

        if (telefone != null && !telefone.isEmpty()) {
            txtTelefone.setText(telefone);
        }

        if (horarioAbertura != null && horarioFechamento != null) {
            txtFuncionamento.setText(
                    "Seg. a Sex. • " +
                            horarioAbertura +
                            " às " +
                            horarioFechamento
            );
        }

        if (tipo != null && !tipo.isEmpty()) {
            txtCategoria.setText(tipo);
        }

        // Botão voltar
        btnVoltar.setOnClickListener(v -> finish());
    }
}

