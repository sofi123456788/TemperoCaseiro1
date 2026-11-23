package com.example.temperocaseiro1;

/**
 * Tela independente para Atendimento com IA.
 *
 * Reutiliza a conexão existente com o backend Aurora. Não altera o modelo
 * Claude, as instruções do agente nem as regras de avaliação do Motor V3.
 * Neste estágio as duas interfaces utilizam o endpoint anônimo disponível.
 */
public class AtendimentoIAActivity extends ChatAnonimoActivity {
    @Override
    protected int getLayoutChat() {
        return R.layout.activity_atendimento_ia;
    }

    @Override
    protected String mensagemInicial() {
        return "Olá! Sou a Aurora, assistente virtual de acolhimento. "
                + "Conte o que está acontecendo, no seu tempo. "
                + "Se houver perigo imediato, procure um local seguro e ligue 190.";
    }
}
