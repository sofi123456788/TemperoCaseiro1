async function mostrarNomeCentro() {
    const listaCentro = document.getElementById("listaCentros");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/editarCentroApoio`);
        const centros = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaCentro.innerHTML = '';
        for (let i = 0; i < centros.length; i++) {
            const nomeCentro = centros[i];
            const itemLI = document.createElement('li');
            itemLI.dataset.id = nomeCentro.id;
            itemLI.dataset.nome = nomeCentro.nome;
            itemLI.textContent = `${nomeCentro.nome}`;
            listaCentro.appendChild(itemLI);
        }
    } catch (error) {
        alert("Não foi possível encontrar os centro de apoio");
    }
}

async function pegarInfosCentro() {
    try {
        const listaCentro = document.getElementById("listaCentros");
        const idCentro = event.target.dataset.id;
        const nomeCentro = event.target.dataset.nome;
        idCentroSelecionado = event.target.dataset.id;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/editarCentroApoio/${idCentro}/${nomeCentro}`);
        const infoCentro = await resposta.json();

        //Mostra as infos para o profissional
        document.getElementById("nomeCentro").value = infoCentro.nome;
        document.getElementById("telefoneCentro").value = infoCentro.telefone;
        document.getElementById("emailCentro").value = infoCentro.email;
        document.getElementById("ruaCentro").value = infoCentro.rua;
        document.getElementById("logradouroCentro").value = infoCentro.logradouro;
        document.getElementById("bairroCentro").value = infoCentro.bairro;
        document.getElementById("cidadeCentro").value = infoCentro.cidade;
        document.getElementById("estadoCentro").value = infoCentro.estado;
        document.getElementById("horaACentro").value = infoCentro.horario_abertura;
        document.getElementById("horaFCentro").value = infoCentro.horario_fechamento;
        document.getElementById("tipoCentro").value = infoCentro.tipo;
    } catch (error) {
        alert("Erro ao recolher informações do Centro!")
    }
    
}

async function alterarInfosCentro() {
    event.preventDefault();

    //Pega as infos nos campos
    const nomeNovo = document.getElementById("nomeCentro").value;
    const telefoneNovo = document.getElementById("telefoneCentro").value;
    const emailNovo = document.getElementById("emailCentro").value;
    const ruaNovo = document.getElementById("ruaCentro").value;
    const logradouroNovo = document.getElementById("logradouroCentro").value;
    const bairroNovo = document.getElementById("bairroCentro").value;
    const cidadeNovo = document.getElementById("cidadeCentro").value;
    const estadoNovo = document.getElementById("estadoCentro").value;
    const horaaNovo = document.getElementById("horaACentro").value;
    const horafNovo = document.getElementById("horaFCentro").value;
    const tipoNovo = document.getElementById("tipoCentro").value;

    //Envia as alterações pára o banco
    try {
        console.log("Enviando dados...");
        //Manda as infos coletadas para o server
        const idCentro = idCentroSelecionado;
        const resposta = await fetch(`http://localhost:3000/editarCentroApoio/${idCentro}`,{
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nomeNovo, telefoneNovo,emailNovo,ruaNovo,logradouroNovo,bairroNovo,cidadeNovo,estadoNovo,horaaNovo,horafNovo,tipoNovo})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        //Confirma que deu tudo certo
        if (resposta.ok) {
            alert("Centro alterado com sucesso!");
            window.location.reload();
        }
    } catch (erro) {
        console.error("Erro:", erro);
        alert("Não foi possível realizar essas alterações!");
    }
}

//Roda a função
document.addEventListener("DOMContentLoaded", mostrarNomeCentro);