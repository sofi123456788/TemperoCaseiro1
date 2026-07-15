// Adicionar restaurante
document.getElementById("container").addEventListener("submit", async (event) => {

    event.preventDefault();

    console.log("Formulário enviado")

    const nome = document.getElementById("nome").value;
    const telefone = document.getElementById("telefone").value;
    const cep = document.getElementById("cep").value;
    const rua = document.getElementById("rua").value;
    const logradouro = document.getElementById("logradouro").value;
    const bairro = document.getElementById("bairro").value;
    const cidade = document.getElementById("cidade").value;
    const estado = document.getElementById("estado").value;
    const horaA = document.getElementById("horaA").value;
    const horaF = document.getElementById("horaF").value;

    try {
        console.log("Enviando dados...");
        const resposta = await fetch("http://localhost:3000/addRestaurante",{
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nome, telefone, cep, rua, logradouro, bairro, cidade, estado, horaA, horaF})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        if (resposta.ok) {
            texto.mensagem;
            window.location.href = "addRestaurante.html";
        }
    } catch (erro) {
        console.erro("Erro:", erro);
    }
});