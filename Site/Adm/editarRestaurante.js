async function mostrarNomeRestaurantes() {
    const listaRestaurante = document.getElementById("listaRestaurantes");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/Adm/editarRestaurante`);
        const rests = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaRestaurante.innerHTML = '';
        for (let i = 0; i < rests.length; i++) {
            const nomeRestaurante = rests[i];
            const itemLI = document.createElement('li');
            itemLI.dataset.id = nomeRestaurante.id;
            itemLI.dataset.nome = nomeRestaurante.nome;
            itemLI.textContent = `${nomeRestaurante.nome}`;
            listaRestaurante.appendChild(itemLI);
        }
    } catch (error) {
        alert("Não foi possível encontrar os restaurantes");
    }
}

async function pegarInfosRestaurantes(event) {
    try {
        const idRestaurante = event.target.dataset.id;
        idRestauranteSelecionado = idRestaurante;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/Adm/editarRestaurante/${idRestaurante}`);

        if (!resposta.ok) {
            throw new Error(`Erro HTTP: ${resposta.status}`);
        }

        const infoRestaurante = await resposta.json();

        //Mostra as infos para o profissional
        document.getElementById("nomeRestaurante").value = infoRestaurante.nome;
        document.getElementById("telefoneRestaurante").value = infoRestaurante.telefone;
        document.getElementById("cepRestaurante").value = infoRestaurante.cep;
        document.getElementById("ruaRestaurante").value = infoRestaurante.rua;
        document.getElementById("logradouroRestaurante").value = infoRestaurante.logradouro;
        document.getElementById("bairroRestaurante").value = infoRestaurante.bairro;
        document.getElementById("cidadeRestaurante").value = infoRestaurante.cidade;
        document.getElementById("estadoRestaurante").value = infoRestaurante.estado;
        document.getElementById("horaARestaurante").value = infoRestaurante.horario_abertura;
        document.getElementById("horaFRestaurante").value = infoRestaurante.horario_fechamento;
    } catch (error) {
        alert("Erro ao recolher informações do Restaurante!")
    }
    
}

async function alterarInfosRestaurante(event) {
    event.preventDefault();

    //Pega as infos nos campos
    const nomeNovo = document.getElementById("nomeRestaurante").value;
    const telefoneNovo = document.getElementById("telefoneRestaurante").value;
    const cepNovo = document.getElementById("cepRestaurante").value;
    const ruaNovo = document.getElementById("ruaRestaurante").value;
    const logradouroNovo = document.getElementById("logradouroRestaurante").value;
    const bairroNovo = document.getElementById("bairroRestaurante").value;
    const cidadeNovo = document.getElementById("cidadeRestaurante").value;
    const estadoNovo = document.getElementById("estadoRestaurante").value;
    const horaaNovo = document.getElementById("horaARestaurante").value;
    const horafNovo = document.getElementById("horaFRestaurante").value;

    //Envia as alterações pára o banco
    try {
        console.log("Enviando dados...");
        //Manda as infos coletadas para o server
        const idRestaurante = idRestauranteSelecionado;
        const resposta = await fetch(`http://localhost:3000/Adm/editarRestaurante/${idRestaurante}`,{
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nomeNovo, telefoneNovo,cepNovo,ruaNovo,logradouroNovo,bairroNovo,cidadeNovo,estadoNovo,horaaNovo,horafNovo})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        //Confirma que deu tudo certo
        if (resposta.ok) {
            alert("Restaurante alterado com sucesso!");
            window.location.reload();
        }
    } catch (erro) {
        console.error("Erro:", erro);
        alert("Não foi possível realizar essas alterações!");
    }
}

//Função para apagar restaurante
async function excluirRestaurante() {

    //Confirma se é desejo do usuário
    const resposta = confirm(
        "Tem certeza que deseja excluir esse restaurante?"
    );

    //Caso seja negativo não acontece nada
    if (!resposta) {
        return;
    }
    try {
        //Pega o id e manda para o server
        const idRestaurante = idRestauranteSelecionado;

        const requisicao = await fetch(`http://localhost:3000/Adm/excluirRestaurante/${idRestaurante}`,{method: "DELETE"});

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

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", mostrarNomeRestaurantes);