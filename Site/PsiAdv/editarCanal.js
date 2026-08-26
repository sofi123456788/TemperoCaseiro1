async function mostrarNomeCanal() {
    const listaCanal = document.getElementById("listaCanais");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const email = localStorage.getItem("emailUsuario");
        const resposta = await fetch(`http://localhost:3000/editarCanal/${email}`);
        const canais = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaCanal.innerHTML = '';
        for (let i = 0; i < canais.length; i++) {
            const nomeC= canais[i];
            const itemLI = document.createElement('li');
            itemLI.dataset.id = nomeC.id;
            itemLI.dataset.nome = nomeC.nome;
            itemLI.textContent = `${nomeC.nome}`;
            listaCanal.appendChild(itemLI);
        }
    } catch (error) {
        
    }
}

async function pegarInfos() {
    try {
        const listaCanal = document.getElementById("listaCanais");
        const idCanal = event.target.dataset.id;
        const nomeCanal = event.target.dataset.nome;
        idCanalSelecionado = event.target.dataset.id;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/editarCanal/${idCanal}/${nomeCanal}`);
        const infoCanal = await resposta.json();

        //Mostra as infos para o profissional
        document.getElementById("nomeCanal").value = infoCanal.nome;
        document.getElementById("telefoneCanal").value = infoCanal.telefone;
        document.getElementById("emailCanal").value = infoCanal.email;
        document.getElementById("ruaCanal").value = infoCanal.rua;
        document.getElementById("logradouroCanal").value = infoCanal.logradouro;
        document.getElementById("bairroCanal").value = infoCanal.bairro;
        document.getElementById("cidadeCanal").value = infoCanal.cidade;
        document.getElementById("estadoCanal").value = infoCanal.estado;
        document.getElementById("horaACanal").value = infoCanal.horario_abertura;
        document.getElementById("horaFCanal").value = infoCanal.horario_fechamento;
        document.getElementById("modoCanal").value = infoCanal.modo;
    } catch (error) {
        alert("Erro ao recolher informações do canal!")
    }
    
}

async function alterarInfos() {
    event.preventDefault();

    //Pega as infos nos campos
    const nomeNovo = document.getElementById("nomeCanal").value;
    const telefoneNovo = document.getElementById("telefoneCanal").value;
    const emailNovo = document.getElementById("emailCanal").value;
    const ruaNovo = document.getElementById("ruaCanal").value;
    const logradouroNovo = document.getElementById("logradouroCanal").value;
    const bairroNovo = document.getElementById("bairroCanal").value;
    const cidadeNovo = document.getElementById("cidadeCanal").value;
    const estadoNovo = document.getElementById("estadoCanal").value;
    const horaaNovo = document.getElementById("horaACanal").value;
    const horafNovo = document.getElementById("horaFCanal").value;
    const modoNovo = document.getElementById("modoCanal").value;

    //Envia as alterações pára o banco
    try {
        console.log("Enviando dados...");
        //Manda as infos coletadas para o server
        const idC = idCanalSelecionado;
        const resposta = await fetch(`http://localhost:3000/editarCanal/${idC}`,{
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nomeNovo, telefoneNovo,emailNovo,ruaNovo,logradouroNovo,bairroNovo,cidadeNovo,estadoNovo,horaaNovo,horafNovo,modoNovo})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        //Confirma que deu tudo certo
        if (resposta.ok) {
            alert("Canal alterado com sucesso!");
            window.location.reload();
        }
    } catch (erro) {
        console.error("Erro:", erro);
        alert("Nã foi possível realizar essas alterações!");
    }
}

//Função para apagar canal
async function excluirCanal() {

    //Confirma se é desejo do usuário
    const resposta = confirm(
        "Tem certeza que deseja excluir esse Canal?"
    );

    //Caso seja negativo não acontece nada
    if (!resposta) {
        return;
    }
    try {
        //Pega o id e manda para o server
        const idCanal = idCanalSelecionado;

        const requisicao = await fetch(`http://localhost:3000/excluirCanal/${idCanal}`,{method: "DELETE"});

        const mensagem = await requisicao.text();

        alert(mensagem);

        //Limpa as infos que estavam guardadas temporariamente
        if (requisicao.ok) {
            localStorage.clear();
            window.location.reload();
        }

    } catch (erro) {
        console.error(erro);
        alert("Não foi possível conectar ao servidor.");
    }
    
}

//Roda a função
document.addEventListener("DOMContentLoaded", mostrarNomeCanal);