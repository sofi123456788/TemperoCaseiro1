// Mostrar o nome do arquivo escolhido
document.getElementById("documento").addEventListener("change", function () {//seleciona o arquivo

    const arquivo = this.files[0];

    if (arquivo) {
        document.getElementById("nomeArquivo").textContent =
            arquivo.name;
    }

});


// Cadastrar usuário
document.getElementById("form").addEventListener("submit", async (event) => {

    event.preventDefault();

    console.log("Formulário enviado")

    const nome_completo = document.getElementById("nome_completo").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;
    const cpf = document.getElementById("cpf").value;
    const senha = document.getElementById("senha").value;
    const Csenha = document.getElementById("Csenha").value;
    const area_profissional = document.getElementById("area_profissional").value;

    if (senha !== Csenha) {
        alert("As senhas não coincidem.");
        return;
    }

    try {
        console.log("Enviando dados...");
        const resposta = await fetch("http://localhost:3000/cadastro",{
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nome_completo, telefone, email, cpf, senha, area_profissional})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        if (resposta.ok) {
            window.location.href = "index.html";
        }
    } catch (erro) {
        console.erro("Erro:", erro);
    }
    

});