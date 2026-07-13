window.addEventListener("load", async () => {

    const email = localStorage.getItem("emailUsuario");

    const resposta = await fetch(
        `http://localhost:3000/perfil/${email}`
    );

    const usuario = await resposta.json();

    document.getElementById("nomePerfil").textContent = usuario.nome_completo;

    document.getElementById("nome").textContent = usuario.nome_completo;

    document.getElementById("email").textContent = usuario.email;

    document.getElementById("telefone").textContent = usuario.telefone;

    document.getElementById("cpf").textContent = usuario.cpf;

    document.getElementById("area").textContent = usuario.area_profissional;
});

async function excluirConta() {

    const resposta = confirm(
        "Tem certeza que deseja excluir sua conta?"
    );

    if (!resposta) {
        return;
    }

    const id = localStorage.getItem("idUsuario");

    const requisicao = await fetch(
        `http://localhost:3000/excluirConta/${id}`,{
            method: "DELETE"
        }
    );

    const mensagem = await requisicao.text();

    alert(mensagem);

    if (requisicao.ok) {

        localStorage.clear();

        window.location.href = "login.html";
    }
}