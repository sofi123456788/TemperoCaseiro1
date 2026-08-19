async function mostrarNomeRestaurantes() {
    const listaRestaurante = document.getElementById("listaRestaurantes");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/editarRestaurante`);
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

async function pegarInfosRestaurantes() {
    try {
        const listaRestaurante = document.getElementById("listaRestaurantes");
        const idRestaurante = event.target.dataset.id;
        const nomeRestaurante = event.target.dataset.nome;
        idRestauranteSelecionado = event.target.dataset.id;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/editarRestaurante/${idRestaurante}/${nomeRestaurante}`);
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
        const resposta = await fetch(`http://localhost:3000/editarRestaurante/${idRestaurante}`,{
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

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", mostrarNomeRestaurantes);