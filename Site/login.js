document.getElementById("formLogin").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Pega os dados digitados
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    try {
        // Envia para o servidor
        const resposta = await fetch("http://localhost:3000/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, senha })
        });

        const mensagem = await resposta.text();

        // Se o login deu certo
        if (resposta.ok) {
            alert(mensagem);
            localStorage.setItem("emailUsuario", email);

            // Vai para a tela inicial
            window.location.href = "telaInicial.html";
        } else {
            alert(mensagem);
        }

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível conectar ao servidor.");
    }

});