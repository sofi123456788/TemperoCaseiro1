//Carrega as info de usuário no BD
window.addEventListener("load", async () => {

    const email = localStorage.getItem("emailUsuario");

    //Manda para o server o email do usuário para pegar as demais infos
    const resposta = await fetch(`http://localhost:3000/perfil/${email}`);

    //Expõe as infos
    const usuario = await resposta.json();

    document.getElementById("nomePerfil").textContent = usuario.nome_completo;

    document.getElementById("nome").textContent = usuario.nome_completo;

    document.getElementById("email").textContent = usuario.email;

    document.getElementById("telefone").textContent = usuario.telefone;

    document.getElementById("cpf").textContent = usuario.cpf;

    document.getElementById("area").textContent = usuario.area_profissional;
});

//Excluir Conta
document.getElementById("btnD").addEventListener("submit", async (e) => {
    e.preventDefault();
    //Confirma se é desejo do usuário
    const resposta = confirm(
        "Tem certeza que deseja excluir sua conta?"
    );
    if(!resposta){
        return;
    }else{
        try {
            //Pega o id e manda para o server
            const id = localStorage.getItem("id");

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

        } catch (erro) {
            console.error(erro);
            alert("Não foi possível conectar ao servidor.");
        }
    }
});

//Função para apagar conta
async function excluirConta() {

    //Confirma se é desejo do usuário
    const resposta = confirm(
        "Tem certeza que deseja excluir sua conta?"
    );

    //Caso seja negativo não acontece nada
    if (!resposta) {
        return;
    }
    try {
        //Pega o id e manda para o server
        const id = localStorage.getItem("id");

        const requisicao = await fetch(`http://localhost:3000/excluirConta/${id}`,{method: "DELETE"});

        const mensagem = await requisicao.text();

        alert(mensagem);

        //Limpa as infos que estavam guardadas temporariamente
        if (requisicao.ok) {
            localStorage.clear();
            window.location.href = "index.html";
        }

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível conectar ao servidor.");
    }
    
}

//Função para deslogar conta
async function deslogar() {

    //Confirma se é desejo do usuário
    const resposta = confirm(
        "Tem certeza que deseja sair de sua conta?"
    );

    //Caso seja negativo não acontece nada
    if (!resposta) {
        return;
    }
        try {
            //Limpa todas as infos pegas
            localStorage.clear();
            //Volta para a tela de login
            window.location.href = "index.html";
        } catch (erro) {
            console.error(erro);
            alert("Não foi possível conectar ao servidor.");
        }
    
}