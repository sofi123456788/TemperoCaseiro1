
package com.example.temperocaseiro1;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatAnonimoActivity extends AppCompatActivity {

    // Emulador: 10.0.2.2. Celular físico: localhost encaminhado pelo ADB.
    // Execute no Fedora: adb reverse tcp:8000 tcp:8000
    // O servidor Python pode continuar restrito a 127.0.0.1.
    private static String enderecoAurora() {
        String hardware = android.os.Build.HARDWARE.toLowerCase();
        boolean emulador = android.os.Build.FINGERPRINT.startsWith("generic")
                || hardware.contains("ranchu") || hardware.contains("goldfish")
                || android.os.Build.MODEL.contains("Emulator");
        return emulador ? "http://10.0.2.2:8000" : "http://127.0.0.1:8000";
    }

    private TextView txtStatus;
    private EditText editMensagem;
    private Button btnEnviar;
    private LinearLayout containerMensagens;
    private ScrollView scrollChat;

    private String tokenSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutChat());
        BarrasSistema.aplicar(this);
        findViewById(R.id.btnVoltarChat).setOnClickListener(v -> finish());

        // Botão opcional: volta à tela inicial do aparelho. Não apaga histórico.
        View sair = findViewById(R.id.btnSaidaRapida);
        boolean habilitado = getSharedPreferences(FerramentasSegurancaActivity.PREFS, MODE_PRIVATE)
                .getBoolean(FerramentasSegurancaActivity.SAIDA, true);
        sair.setVisibility(habilitado ? View.VISIBLE : View.GONE);
        sair.setOnClickListener(v -> {
            Intent inicio = new Intent(Intent.ACTION_MAIN);
            inicio.addCategory(Intent.CATEGORY_HOME);
            inicio.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inicio);
        });

        txtStatus = findViewById(R.id.txtStatusChat);
        editMensagem = findViewById(R.id.editMensagem);
        btnEnviar = findViewById(R.id.btnEnviarMensagem);
        containerMensagens = findViewById(R.id.containerMensagens);
        scrollChat = findViewById(R.id.scrollChat);

        btnEnviar.setEnabled(false);

        // A mensagem inicial aparece mesmo se o servidor não estiver acessível.
        adicionarMensagem("Aurora", mensagemInicial());
        iniciarSessao();

        btnEnviar.setOnClickListener(v -> enviarMensagem());
    }

    /** Permite que AtendimentoIAActivity utilize layout próprio e a mesma API Aurora. */
    protected int getLayoutChat() { return R.layout.activity_chat_anonimo; }

    protected String mensagemInicial() { return "Olá! Este é um espaço de acolhimento. Você pode começar contando o que gostaria de conversar."; }

    private JSONObject requisicao(String caminho, JSONObject dados)
            throws Exception {

        URL url = new URL(enderecoAurora() + caminho);
        HttpURLConnection conexao =
                (HttpURLConnection) url.openConnection();

        try {
            conexao.setRequestMethod("POST");
            conexao.setConnectTimeout(10000);
            conexao.setReadTimeout(30000);
            conexao.setDoOutput(true);
            conexao.setRequestProperty(
                    "Content-Type", "application/json; charset=UTF-8"
            );

            byte[] corpo = dados.toString()
                    .getBytes(StandardCharsets.UTF_8);

            try (OutputStream saida = conexao.getOutputStream()) {
                saida.write(corpo);
            }

            int status = conexao.getResponseCode();

            InputStream entrada = status >= 400
                    ? conexao.getErrorStream()
                    : conexao.getInputStream();

            if (entrada == null) {
                throw new Exception("Erro HTTP " + status);
            }

            byte[] resposta;
            try (InputStream stream = entrada) {
                resposta = stream.readAllBytes();
            }

            JSONObject json = new JSONObject(
                    new String(resposta, StandardCharsets.UTF_8)
            );

            if (status >= 400) {
                throw new Exception("HTTP " + status + ": " +
                        json.optString("detail", "Serviço indisponível"));
            }

            return json;

        } finally {
            conexao.disconnect();
        }
    }

    private void iniciarSessao() {
        txtStatus.setText("Conectando à Aurora...");

        new Thread(() -> {
            try {
                JSONObject resposta = requisicao(
                        "/api/anonimo/iniciar",
                        new JSONObject()
                );

                tokenSessao = resposta.getString("token");

                runOnUiThread(() -> {
                    txtStatus.setText("Sessão anônima ativa");
                    btnEnviar.setEnabled(true);

                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        txtStatus.setText("Não foi possível iniciar a conversa. " +
                                "Confira a API, o banco de dados e a conexão USB.")
                );
            }
        }).start();
    }

    private void enviarMensagem() {
        String mensagem = editMensagem.getText().toString().trim();

        if (mensagem.isEmpty() || tokenSessao == null) {
            return;
        }

        editMensagem.setText("");
        adicionarMensagem("Você", mensagem);

        btnEnviar.setEnabled(false);
        txtStatus.setText("Aurora está respondendo...");

        new Thread(() -> {
            try {
                JSONObject dados = new JSONObject();
                dados.put("token", tokenSessao);
                dados.put("mensagem", mensagem);

                JSONObject resposta = requisicao(
                        "/api/anonimo/chat",
                        dados
                );

                JSONObject respostaIA =
                        resposta.getJSONObject("resposta_ia");

                String texto = respostaIA.optString(
                        "texto_resposta",
                        "Não foi possível obter uma resposta."
                );

                String nivel = respostaIA.optString(
                        "nivel_atual", "Verde"
                );

                boolean ajuda = respostaIA.optBoolean(
                        "sugerir_ajuda", false
                );

                boolean emergencia = respostaIA.optBoolean(
                        "emergencia", false
                );

                runOnUiThread(() -> {
                    adicionarMensagem("Aurora", texto);
                    txtStatus.setText("Nível: " + nivel);

                    if (ajuda && mostrarAvisos()) {
                        adicionarMensagem(
                                "Apoio",
                                "Central de Atendimento à Mulher: 180"
                        );
                    }

                    if (emergencia) {
                        adicionarMensagem(
                                "Emergência",
                                "Em perigo imediato, ligue 190."
                        );
                    }

                    btnEnviar.setEnabled(true);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    String erro = e.getMessage() == null ? "" : e.getMessage();
                    if (erro.contains("503") || erro.toLowerCase().contains("anthropic")) {
                        adicionarMensagem("Sistema",
                                "O atendimento está temporariamente indisponível. " +
                                "Tente novamente mais tarde.");
                        txtStatus.setText("Atendimento temporariamente indisponível");
                    } else if (erro.contains("500")) {
                        adicionarMensagem("Sistema",
                                "O servidor encontrou um erro. Verifique o banco " +
                                "de dados e o terminal da Aurora.");
                        txtStatus.setText("Erro no servidor");
                    } else {
                        adicionarMensagem("Sistema",
                                "Não foi possível enviar. Confira a conexão com a Aurora.");
                        txtStatus.setText("Falha na comunicação");
                    }
                    btnEnviar.setEnabled(true);
                });
            }
        }).start();
    }

    private boolean mostrarAvisos() {
        return getSharedPreferences(FerramentasSegurancaActivity.PREFS, MODE_PRIVATE)
                .getBoolean(FerramentasSegurancaActivity.AVISOS, true);
    }

    /** Mensagens em balões que acompanham a largura da tela. */
    private void adicionarMensagem(String autor, String conteudo) {
        boolean doUsuario = "Você".equals(autor);
        TextView mensagem = new TextView(this);
        mensagem.setText(autor + ":\n" + conteudo);
        mensagem.setTextSize(15);
        mensagem.setTextColor(Color.rgb(38, 53, 38));

        float densidade = getResources().getDisplayMetrics().density;
        int padding = (int)(13 * densidade);
        mensagem.setPadding(padding, padding, padding, padding);
        mensagem.setMaxWidth((int)(getResources().getDisplayMetrics().widthPixels * 0.82f));

        GradientDrawable fundo = new GradientDrawable();
        fundo.setColor(Color.parseColor(doUsuario ? "#E3EDD9" : "#FFFFFF"));
        fundo.setCornerRadius(18 * densidade);
        fundo.setStroke(Math.max(1, (int)densidade), Color.parseColor("#E5D9C8"));
        mensagem.setBackground(fundo);

        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        parametros.gravity = doUsuario ? Gravity.END : Gravity.START;
        parametros.setMargins(0, 0, 0, padding);
        containerMensagens.addView(mensagem, parametros);
        scrollChat.post(() -> scrollChat.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
