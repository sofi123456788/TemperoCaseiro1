
        package com.example.temperocaseiro1;

import android.os.Bundle;
import android.widget.TextView;

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

