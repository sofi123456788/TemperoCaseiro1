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

import android.provider.OpenableColumns;
import android.database.Cursor;
import android.content.ContentResolver;

import com.example.temperocaseiro1.model.DenunciaCriadaResponse;

import java.io.InputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

        DenuciasRequest request = new DenuciasRequest(tipoViolenciaSelecionada, relatoViolencia);

        AuthApi api = ApiClient.getRetrofit().create(AuthApi.class);

        btnEnviarDenuncia.setEnabled(false);

        api.criarDenuncia(request).enqueue(new Callback<DenunciaCriadaResponse>() {

            @Override
            public void onResponse(Call<DenunciaCriadaResponse> call, Response<DenunciaCriadaResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    btnEnviarDenuncia.setEnabled(true);
                    Toast.makeText(tela_denuncia.this, "Erro ao criar denúncia: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Long denunciaId = response.body().getId();

                if (denunciaId == null) {
                    btnEnviarDenuncia.setEnabled(true);
                    Toast.makeText(tela_denuncia.this, "A API não retornou o ID da denúncia", Toast.LENGTH_LONG).show();
                    return;
                }

                java.util.ArrayList<Uri> anexos = new java.util.ArrayList<>();

                if (fotoSelecionadaUri != null) {
                    anexos.add(fotoSelecionadaUri);
                }

                if (audioSelecionadoUri != null) {
                    anexos.add(audioSelecionadoUri);
                }

                if (videoSelecionadoUri != null) {
                    anexos.add(videoSelecionadoUri);
                }

                enviarProximoAnexo(api, denunciaId, anexos, 0, 0);
            }

            @Override
            public void onFailure(Call<DenunciaCriadaResponse> call, Throwable t) {
                btnEnviarDenuncia.setEnabled(true);
                Toast.makeText(tela_denuncia.this, "Falha ao criar denúncia: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void enviarProximoAnexo(
            AuthApi api,
            Long denunciaId,
            java.util.ArrayList<Uri> anexos,
            int indice,
            int enviados) {

        if (indice >= anexos.size()) {
            btnEnviarDenuncia.setEnabled(true);

            Toast.makeText(this,
                    "Denúncia salva! Anexos enviados: "
                            + enviados + "/" + anexos.size(),
                    Toast.LENGTH_LONG).show();
            return;
        }

        try {
            MultipartBody.Part parte = prepararArquivo(anexos.get(indice));

            api.enviarAnexo(denunciaId, parte).enqueue(new Callback<String>() {
                @Override
                public void onResponse(
                        Call<String> call,
                        Response<String> response) {

                    int totalEnviados = enviados;

                    if (response.isSuccessful()) {
                        totalEnviados++;
                    } else {
                        Toast.makeText(tela_denuncia.this,
                                "Erro no anexo " + (indice + 1)
                                        + ": " + response.code(),
                                Toast.LENGTH_LONG).show();
                    }

                    enviarProximoAnexo(
                            api, denunciaId, anexos,
                            indice + 1, totalEnviados);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(tela_denuncia.this,
                            "Falha no anexo " + (indice + 1),
                            Toast.LENGTH_LONG).show();

                    enviarProximoAnexo(
                            api, denunciaId, anexos,
                            indice + 1, enviados);
                }
            });

        } catch (Exception e) {
            Toast.makeText(this,
                    "Erro ao preparar anexo " + (indice + 1),
                    Toast.LENGTH_LONG).show();

            enviarProximoAnexo(
                    api, denunciaId, anexos,
                    indice + 1, enviados);
        }
    }

    private MultipartBody.Part prepararArquivo(Uri uri) throws IOException {

        ContentResolver resolver = getContentResolver();

        String tipo = resolver.getType(uri);

        if (tipo == null) {
            tipo = "application/octet-stream";
        }

        String extensao;

        switch (tipo) {
            case "image/jpeg":
                extensao = ".jpg";
                break;
            case "image/png":
                extensao = ".png";
                break;
            case "image/webp":
                extensao = ".webp";
                break;
            case "audio/mpeg":
                extensao = ".mp3";
                break;
            case "audio/wav":
                extensao = ".wav";
                break;
            case "audio/ogg":
                extensao = ".ogg";
                break;
            case "audio/mp4":
                extensao = ".m4a";
                break;
            case "video/mp4":
                extensao = ".mp4";
                break;
            case "video/webm":
                extensao = ".webm";
                break;
            case "video/3gpp":
                extensao = ".3gp";
                break;
            default:
                extensao = ".bin";
        }

        java.io.ByteArrayOutputStream buffer =
                new java.io.ByteArrayOutputStream();

        try (InputStream entrada = resolver.openInputStream(uri)) {
            if (entrada == null) {
                throw new IOException("Arquivo indisponível");
            }

            byte[] bloco = new byte[8192];
            int lidos;

            while ((lidos = entrada.read(bloco)) != -1) {
                if (buffer.size() + lidos > 15 * 1024 * 1024) {
                    throw new IOException("Arquivo maior que 15 MB");
                }
                buffer.write(bloco, 0, lidos);
            }
        }

        RequestBody corpo = RequestBody.create(
                MediaType.parse(tipo),
                buffer.toByteArray()
        );

        return MultipartBody.Part.createFormData(
                "arquivo",
                "anexo_" + System.currentTimeMillis() + extensao,
                corpo
        );
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