// Adicionar restaurante
document.getElementById("container").addEventListener("submit", async (event) => {

    event.preventDefault();

    console.log("Formulário enviado")

    const nome = document.getElementById("nome").value;
    const data = document.getElementById("data").value;
    const tempo = document.getElementById("tempo").value;
    const consideracoes = document.getElementById("consideracoes").value;
    const modo = document.getElementById("modo").value;

    try {
        console.log("Enviando dados...");
        const resposta = await fetch("http://localhost:3000/registrarAtend",{
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nome, data, tempo, consideracoes, modo})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        if (resposta.ok) {
            texto.mensagem;
            window.location.reload();
        }
    } catch (erro) {
        console.erro("Erro:", erro);
    }
});