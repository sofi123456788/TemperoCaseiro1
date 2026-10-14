package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.DenuciasRequest;

// seletor de arquivos do próprio android
import android.net.Uri;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tela_denuncia extends AppCompatActivity {

    private String tipoViolencia = "";
    private LinearLayout layoutFisica;
    private LinearLayout layoutPsicologica;
    private LinearLayout layoutSexual;
    private LinearLayout layoutPatrimonial;
    private LinearLayout layoutAmeaca;
    private LinearLayout layoutOutra;
    private Button btnEnviarDenuncia;
    private EditText editRelato;
    private LinearLayout btnFoto;
    private Uri fotoSelecionadaUri;

    private ImageView imgPreviaFoto;

    private Button btnRemoverFoto;
    private LinearLayout btnAudio;
    private Button btnRemoverAudio;
    private TextView txtAudioSelecionado;
    private Uri audioSelecionadoUri;
    private LinearLayout btnVideo;
    private Button btnRemoverVideo;
    private VideoView videoPrevia;
    private Uri videoSelecionadoUri;

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
        btnFoto = findViewById(R.id.btnFoto);
        imgPreviaFoto = findViewById(R.id.imgPreviaFoto);
        btnRemoverFoto = findViewById(R.id.btnRemoverFoto);

        btnAudio = findViewById(R.id.btnAudio);
        btnRemoverAudio = findViewById(R.id.btnRemoverAudio);
        txtAudioSelecionado = findViewById(R.id.txtAudioSelecionado);


        btnVideo = findViewById(R.id.btnVideo);
        videoPrevia = findViewById(R.id.videoPrevia);
        btnRemoverVideo = findViewById(R.id.btnRemoverVideo);

        // Abre o seletor de vídeos
        btnVideo.setOnClickListener(v -> {
            seletorVideo.launch("video/*");
        });

        // Reproduz o vídeo ao tocar na prévia
        videoPrevia.setOnClickListener(v -> {
            if (videoPrevia.isPlaying()) {
                videoPrevia.pause();
            } else {
                videoPrevia.start();
            }
        });

        // Remove o vídeo selecionado
        btnRemoverVideo.setOnClickListener(v -> {

            videoPrevia.stopPlayback();
            videoSelecionadoUri = null;

            videoPrevia.setVisibility(View.GONE);
            btnRemoverVideo.setVisibility(View.GONE);

            Toast.makeText(tela_denuncia.this, "Vídeo removido", Toast.LENGTH_SHORT).show();
        });

        // Abre o seletor de áudio
        btnAudio.setOnClickListener(v -> {
            seletorAudio.launch("audio/*");
        });

        // Remove o áudio selecionado
        btnRemoverAudio.setOnClickListener(v -> {
            audioSelecionadoUri = null;

            txtAudioSelecionado.setText("");
            txtAudioSelecionado.setVisibility(View.GONE);
            btnRemoverAudio.setVisibility(View.GONE);

            Toast.makeText(tela_denuncia.this, "Áudio removido", Toast.LENGTH_SHORT).show();
        });


        btnFoto.setOnClickListener(v -> {
            seletorFoto.launch("image/*");
        });


        layoutFisica.setOnClickListener(v -> selecionarViolencia("fisica", layoutFisica));

        layoutPsicologica.setOnClickListener(v -> selecionarViolencia("psicologica", layoutPsicologica));

        layoutSexual.setOnClickListener(v -> selecionarViolencia("sexual", layoutSexual));

        layoutPatrimonial.setOnClickListener(v -> selecionarViolencia("patrimonial", layoutPatrimonial));

        layoutAmeaca.setOnClickListener(v -> selecionarViolencia("ameaca", layoutAmeaca));

        layoutOutra.setOnClickListener(v -> selecionarViolencia("outra", layoutOutra));


        btnRemoverFoto.setOnClickListener(v -> {

            // Remove a referência da fotografia
            fotoSelecionadaUri = null;

            // Limpa a imagem da prévia
            imgPreviaFoto.setImageDrawable(null);

            // Esconde a prévia
            imgPreviaFoto.setVisibility(android.view.View.GONE);

            // Esconde o botão Remover foto
            btnRemoverFoto.setVisibility(android.view.View.GONE);

            Toast.makeText(tela_denuncia.this, "Foto removida", Toast.LENGTH_SHORT).show();
        });


        // verificação do tipo de seleção
        btnEnviarDenuncia.setOnClickListener(v -> {

            // 1. Verificar se algum tipo de violência foi selecionado
            if (tipoViolencia.isEmpty()) {
                Toast.makeText(tela_denuncia.this, "Selecione um tipo de violência", Toast.LENGTH_SHORT).show();
                return;
            }
            // 2. Pega o relato do usuário e transforma em String
            String relatoViolencia = editRelato.getText().toString().trim();

            if (relatoViolencia.isEmpty()) {
                editRelato.setError("Descreva o que ocorreu");
                editRelato.requestFocus();
                return;
            }
            enviarDenunciaParaAPI(tipoViolencia, relatoViolencia);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void enviarDenunciaParaAPI(String tipoViolenciaSelecionada, String relatoViolencia) {

        DenuciasRequest denunciasRequest = new DenuciasRequest(tipoViolenciaSelecionada, relatoViolencia);

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class); // cria uma conexão com a API

        Call<String> call = authApi.denuncia(denunciasRequest);// prepara uma chamada HTTP para o login do usuário

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) { // API respondeu
                if (response.isSuccessful()) {
                    Toast.makeText(tela_denuncia.this, response.body(), Toast.LENGTH_SHORT).show();

                    if ("Denúncia salva com sucesso".equals(response.body())) { // abrir a próxima tela do app
                        // próxima tela do app
                        //Intent intent = new Intent(tela_login.this, tela_configuracoes.class);
                        //intent.putExtra("emailUsuario", email);
                        //startActivity(intent);
                        //finish();
                    }

                } else {
                    Toast.makeText(tela_denuncia.this, "Erro ao enviar denúncia. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(tela_denuncia.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void selecionarViolencia(String tipo, LinearLayout selecionado) {

        // Verifica se o usuário tocou na opção já selecionada
        boolean desselecionar = tipoViolencia.equals(tipo);

        // Restaura a aparência de todas as opções
        LinearLayout[] opcoes = {layoutFisica, layoutPsicologica, layoutSexual, layoutPatrimonial, layoutAmeaca, layoutOutra};

        for (LinearLayout opcao : opcoes) {
            opcao.setBackgroundResource(R.drawable.bg_item_denuncia);
        }

        // Se tocou novamente na mesma opção, desmarca
        if (desselecionar) {
            tipoViolencia = "";
            return;
        }

        // Registra a nova seleção
        tipoViolencia = tipo;

        // Aplica o destaque verde
        android.graphics.drawable.GradientDrawable destaque = new android.graphics.drawable.GradientDrawable();

        destaque.setColor(android.graphics.Color.rgb(218, 232, 195));
        destaque.setCornerRadius(18f);
        destaque.setStroke(3, android.graphics.Color.rgb(80, 110, 50));

        selecionado.setBackground(destaque);
    }

    private ActivityResultLauncher<String> seletorFoto = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {

            fotoSelecionadaUri = uri;

            // Exibe a fotografia
            imgPreviaFoto.setImageURI(uri);
            imgPreviaFoto.setVisibility(android.view.View.VISIBLE);

            // Exibe o botão Remover foto
            btnRemoverFoto.setVisibility(android.view.View.VISIBLE);

            Toast.makeText(this, "Foto selecionada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    });


    private final ActivityResultLauncher<String> seletorAudio = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            audioSelecionadoUri = uri;

            txtAudioSelecionado.setText("Áudio selecionado");
            txtAudioSelecionado.setVisibility(View.VISIBLE);
            btnRemoverAudio.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Áudio selecionado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    });


    private final ActivityResultLauncher<String> seletorVideo = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {

            videoSelecionadoUri = uri;

            // Exibe a prévia do vídeo
            videoPrevia.setVideoURI(uri);
            videoPrevia.setVisibility(View.VISIBLE);

            // Mostra o botão de remover
            btnRemoverVideo.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Vídeo selecionado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    });


}