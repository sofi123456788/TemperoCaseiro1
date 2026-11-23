package com.example.temperocaseiro1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/** Navegação da central de ajuda. O botão GRITE! permanece sem ação. */
public class tela_ajuda extends AppCompatActivity {
    private void abrir(Class<?> tela) {
        startActivity(new Intent(this, tela));
    }

    @Override protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        setContentView(R.layout.activity_ajuda);
        com.example.temperocaseiro1.BarrasSistema.aplicar(this);

        findViewById(R.id.btnChatAnonimo).setOnClickListener(v -> abrir(ChatAnonimoActivity.class));
        findViewById(R.id.btnAtendimentoIA).setOnClickListener(v -> abrir(AtendimentoIAActivity.class));
        findViewById(R.id.btnCentrosApoio).setOnClickListener(v -> abrir(CentrosApoioActivity.class));
        findViewById(R.id.btnDenunciaAjuda).setOnClickListener(v -> abrir(tela_denuncia.class));

        // Atalhos do rodapé da central de ajuda.
        findViewById(R.id.menuEntrar).setOnClickListener(v -> com.example.temperocaseiro1.model.SessaoUsuario.abrirInicio(this));
        findViewById(R.id.menuReceitas).setOnClickListener(v -> abrir(ChatAnonimoActivity.class));
        findViewById(R.id.menuFavoritos).setOnClickListener(v -> abrir(CentrosApoioActivity.class));
        findViewById(R.id.menuPerfil).setOnClickListener(v -> abrir(FerramentasSegurancaActivity.class));

        // O botão de menu retorna à tela anterior; não substitui o botão GRITE!.
        findViewById(R.id.btnMenuAjuda).setOnClickListener(v -> finish());
    }
}
