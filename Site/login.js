document.getElementById("form").addEventListener("submit", async (e) => {
    e.preventDefault();

    //Pega as informações
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    //Manda as infos. pro server
    const resposta = await fetch("http://localhost:3000/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, senha })
    });

    alert(await resposta.text());
});

/*
document.getElementById("form").addEventListener("submit", function(event){

    event.preventDefault();

    window.location.href = "telaInicial.html";

});*/