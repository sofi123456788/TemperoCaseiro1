// Adicionar restaurante
document.getElementById("container").addEventListener("submit", async (event) => {

    event.preventDefault();

    console.log("Formulário enviado")

    const nome = document.getElementById("nome").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;
    const rua = document.getElementById("rua").value;
    const logradouro = document.getElementById("logradouro").value;
    const bairro = document.getElementById("bairro").value;
    const cidade = document.getElementById("cidade").value;
    const estado = document.getElementById("estado").value;
    const horaA = document.getElementById("horaA").value;
    const horaF = document.getElementById("horaF").value;
    const modo = document.getElementById("modo").value;

    try {
        console.log("Enviando dados...");
        const resposta = await fetch("http://localhost:3000/addCanal",{
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nome, telefone, email, rua, logradouro, bairro, cidade, estado, horaA, horaF,modo})
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