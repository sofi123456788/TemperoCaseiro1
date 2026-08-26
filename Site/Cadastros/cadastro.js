// Mostrar o nome do arquivo escolhido
document.getElementById("documento").addEventListener("change", function () {//seleciona o arquivo

    const arquivo = this.files[0];

    if (arquivo) {
        document.getElementById("nomeArquivo").textContent =
            arquivo.name;
    }

});

async function enviarInfos() {
    document.getElementById("form").addEventListener('submit', async (e) => {
        e.preventDefault();
        const arquivo = document.getElementById("documento").files[0];

        if (!arquivo) {
            alert("Selecione um arquivo!");
            return;
        }   

        const dados = new FormData();
        dados.append('arquivo', arquivo);

        try {
            const resposta = await fetch(`http://localhost:3000/Docs`,{
                method: "POST",
                body: dados
            });

            const resultado = await resposta.json();

            console.log("Documento salvo!!", resposta.data);
        } catch (erro) {
            console.error("Não salvou broder", erro);
        }
    });


    // Cadastrar usuário
    document.getElementById("form").addEventListener("submit", async (event) => {

        event.preventDefault();

        console.log("Formulário enviado")
        const arquivo = document.getElementById("documento").files[0];

        //Pega As Infos
        const nome_completo = document.getElementById("nome_completo").value;
        const telefone = document.getElementById("telefone").value;
        const email = document.getElementById("email").value;
        const cpf = document.getElementById("cpf").value;
        const senha = document.getElementById("senha").value;
        const Csenha = document.getElementById("Csenha").value;
        const area_profissional = document.getElementById("area_profissional").value;

        //Confirma se as senhas estão iguais
        if (senha !== Csenha) {
            alert("As senhas não coincidem.");
            return;
        }

        try {
            console.log("Enviando dados...");
            //Manda as infos coletadas para o server
            const resposta = await fetch("http://localhost:3000/Cadastros/cadastro",{
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({nome_completo, telefone, email, cpf, senha, area_profissional})
                }
            );
            const texto = await resposta.text();

            console.log(texto);

            //Confirma que deu tudo certo
            if (resposta.ok) {
                window.location.href = "index.html";
            }
        } catch (erro) {
            console.erro("Erro:", erro);
        }

    });
}