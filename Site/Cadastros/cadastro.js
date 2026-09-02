console.log("cadastro.js carregado com sucesso.");

const form = document.getElementById("form");

if (!form) {
    console.error("Elemento #form não foi encontrado no HTML! Verifique o id do <form>.");
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    console.log("Evento de submit disparado.");

    const documento = document.getElementById("documento").files[0];

    if (!documento) {
        alert("Selecione um documento!");
        return;
    }

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
        // 1 - Salva arquivo
        console.log("Enviando documento...");
        const formData = new FormData();
        formData.append("documento", documento);

        const uploadResposta = await fetch("http://localhost:3000/Docs", {
            method: "POST",
            body: formData
        });

        console.log("Upload - status:", uploadResposta.status, "ok:", uploadResposta.ok);

        if (!uploadResposta.ok) {
            alert("Erro ao enviar o documento.");
            return;
        }

        const uploadResultado = await uploadResposta.json();
        const nomeArquivo = uploadResultado.filename;
        console.log("Arquivo salvo como:", nomeArquivo);

        // 2 - Salva cadastro
        console.log("Enviando dados do cadastro...");
        const cadastroResposta = await fetch("http://localhost:3000/Cadastros/cadastro", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nome_completo,
                telefone,
                email,
                cpf,
                senha,
                area_profissional,
                documento: nomeArquivo
            })
        });

        console.log("Cadastro - status:", cadastroResposta.status, "ok:", cadastroResposta.ok);

        let texto;
        try {
            texto = await cadastroResposta.json();
        } catch (parseErro) {
            console.error("Resposta do servidor não é um JSON válido:", parseErro);
            //alert("O servidor retornou uma resposta inesperada. Veja o console para detalhes.");
            //return;
        }

        console.log("Resposta do servidor:", texto);

        if (cadastroResposta.ok) {
            alert(texto.mensagem);
            window.location.href = "aguardandoVerificacao.html";
        } else {
            alert(texto.mensagem || "Erro ao cadastrar.");
        }

    } catch (erro) {
        console.error("Erro capturado no bloco try/catch:", erro);
        alert("Erro: " + erro.message);
    }
});