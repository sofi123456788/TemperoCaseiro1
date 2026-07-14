//Carrega as info de usuário no BD
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