# Integração Aurora — preparação da versão Android

## Estado em 23/11/2026

O aplicativo Android já possui duas telas, `ChatAnonimoActivity` e `AtendimentoIAActivity`, que compartilham a comunicação existente com a API Python da Aurora. Não é necessário adicionar o segredo da Anthropic ao aplicativo.

- Celular físico conectado por USB: usar `adb reverse tcp:8000 tcp:8000`.
- Emulador Android: usa `http://10.0.2.2:8000`.
- Celular físico com ADB reverse: usa `http://127.0.0.1:8000`.
- A API Java do Tempero Caseiro roda separadamente na porta `8080`.
- A API Python Aurora roda na porta `8000` e contém a lógica de geração de resposta.
- Endpoints usados: `POST /api/anonimo/iniciar` e `POST /api/anonimo/chat`.
- O status `200` ao iniciar sessão confirma conexão com a API, **não confirma** a disponibilidade de respostas de IA.
- Na etapa sem chave, foi observado `503` ao enviar mensagens. O aplicativo agora mostra aviso compreensível, sem divulgar a configuração interna da chave.

## Quando a chave chegar

1. No Fedora, iniciar a Aurora Python disponibilizando `ANTHROPIC_API_KEY` **no ambiente do servidor**. Não colocar a chave no Android ou no repositório.
2. Verificar que a API da Aurora iniciou sem erros e que o banco aceita novas sessões.
3. Com celular conectado, executar `adb reverse tcp:8000 tcp:8000`.
4. Abrir a tela Atendimento com IA e iniciar uma conversa de teste com **dados fictícios**.
5. Confirmar que a resposta real da Aurora aparece no balão e que um erro temporário não fecha o app.
6. Conferir que o conteúdo não aparece em logs indevidos, que a saída rápida funciona e que políticas de privacidade/retencão estão revisadas.

## Limites

- Não foi feita validação de respostas reais sem a chave.
- Esta configuração local por USB é adequada para testes, **não substitui** HTTPS e infraestrutura segura em produção.
- O botão Saída rápida abre a tela inicial do dispositivo, mas não elimina notificações, histórico ou aplicativos recentes.
- As mensagens anônimas podem ser armazenadas no servidor. Não prometer anonimato absoluto.
- Nenhuma mudança no modelo Claude, prompt ou Motor V3 original está incluída nesta branch.
