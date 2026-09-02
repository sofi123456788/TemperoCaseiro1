document.getElementById("formLogin").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Pega os dados digitados
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    try {
        // Envia para o servidor
        const resposta = await fetch("http://localhost:3000/Cadastros/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, senha })
        });


        const dados = await resposta.json();

        // Se o login deu certo
        if (resposta.ok) {
            alert(dados.mensagem);
            localStorage.setItem("emailUsuario", email);
            localStorage.setItem("id", dados.id);

            // Vai para a tela inicial Respectiva a área
            if(dados.verifi){
                alert("Login realizado com sucesso!");
                if(dados.area === "Psicologia" || dados.area === "Advocacia"){
                    window.location.href = "/TemperoCaseiro1/Site/PsiAdv/telaInicial.html";
                }else if(dados.area === "Administração"){
                    window.location.href = "/TemperoCaseiro1/Site/Adm/telaInicialA.html";
                }
            }else{
                alert("Sua conta não foi aprovada.");
                window.location.href = "contaNaoAutorizada.html";
            }
                       
        } else if(!resposta.ok){
            const resposta = await fetch("http://localhost:3000/Cadastros/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, senha })
            });
            const dados = await resposta.json();
            if(dados){
                window.location.href = "aguardandoVerificacao.html";
            }
        } else {
            alert(dados.mensagem);
            window.location.href = "contaNaoAutorizada.html";
        }

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível conectar ao servidor.");
    }

});