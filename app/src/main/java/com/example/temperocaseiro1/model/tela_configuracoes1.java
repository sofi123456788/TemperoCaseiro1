package com.example.temperocaseiro1.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.temperocaseiro1.AdicionarReceita;
import com.example.temperocaseiro1.BoasVindasActivity;
import com.example.temperocaseiro1.R;
import com.example.temperocaseiro1.ReceitasBD;
import com.example.temperocaseiro1.tela_ajuda;
import com.example.temperocaseiro1.tela_excluir_conta;

/** Configurações da conta, com navegação consistente com o menu inicial. */
public class tela_configuracoes1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_tela_configuracoes1);

        SessaoUsuario.atualizarDadosSePresentes(
                getIntent().getStringExtra("nomeUsuario"),
                getIntent().getStringExtra("emailUsuario"));

        findViewById(R.id.btnVoltarConfiguracoes).setOnClickListener(v -> finish());

        // Abre a confirmação de exclusão; a exclusão só deve acontecer na tela seguinte.
        findViewById(R.id.layoutExcluirConta).setOnClickListener(v -> {
            Intent intent = new Intent(this, tela_excluir_conta.class);
            intent.putExtra("emailUsuario", SessaoUsuario.getEmail());
            startActivity(intent);
        });

        findViewById(R.id.layoutSobreAplicativo).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Sobre o Tempero Caseiro")
                        .setMessage("Aplicativo de receitas com acesso discreto a recursos de acolhimento e apoio. Versão 1.0.")
                        .setPositiveButton("Fechar", null).show());

        // Mesmo conjunto de destinos presente no rodapé da tela principal.
        findViewById(R.id.menuEntrar).setOnClickListener(v -> SessaoUsuario.abrirInicio(this));
        findViewById(R.id.menuReceitas).setOnClickListener(v ->
                startActivity(new Intent(this, ReceitasBD.class)));
        findViewById(R.id.menuAdicionar).setOnClickListener(v ->
                startActivity(new Intent(this, AdicionarReceita.class)));
        findViewById(R.id.menuPerfil).setOnClickListener(v -> {
            Intent perfil = new Intent(this, tela_perfil.class);
            perfil.putExtra("nomeUsuario", SessaoUsuario.getNomeCompleto());
            perfil.putExtra("emailUsuario", SessaoUsuario.getEmail());
            startActivity(perfil);
        });

        // O acesso protegido é intencionalmente acionado por toque longo.
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable abrirApoio = () -> startActivity(new Intent(this,
                getSharedPreferences("boas_vindas", MODE_PRIVATE)
                        .getBoolean("apresentacao_concluida", false)
                        ? tela_ajuda.class : BoasVindasActivity.class));
        findViewById(R.id.menuFavoritos).setOnTouchListener((v, evento) -> {
            switch (evento.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.postDelayed(abrirApoio, 4000);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler.removeCallbacks(abrirApoio);
                    return true;
                default:
                    return false;
            }
        });
    }
}
