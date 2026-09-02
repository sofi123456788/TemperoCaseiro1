let documentoSelecionado = null;
let idUserSelecionado = null;
async function pegaContasNaoVerificadas() {
    const listaSolicitacao = document.getElementById("listaSolicitacoes");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/Adm/solicitacoes`);
        const rests = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaSolicitacao.innerHTML = '';
        for (let i = 0; i < rests.length; i++) {
            const nomeUser = rests[i];
            const itemLI = document.createElement('li');
            itemLI.dataset.id = nomeUser.id;
            itemLI.dataset.nome = nomeUser.nome_completo;
            itemLI.textContent = `${nomeUser.nome_completo}`;
            listaSolicitacao.appendChild(itemLI);
        }
    } catch (error) {
        alert("Não foi possível encontrar os usuários.");
    }
}

async function pegarInfosUser(event) {
    try {
        const idUser = event.target.dataset.id;
        idUserSelecionado = idUser;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/Adm/solicitacoes/${idUser}`);

        if (!resposta.ok) {
            throw new Error(`Erro HTTP: ${resposta.status}`);
        }

        //Expõe as infos
        const usuario = await resposta.json();

        document.getElementById("nome").textContent = usuario.nome_completo;
        document.getElementById("email").textContent = usuario.email;
        document.getElementById("telefone").textContent = usuario.telefone;
        document.getElementById("cpf").textContent = usuario.cpf;
        document.getElementById("area").textContent = usuario.area_profissional;
        
        documentoSelecionado = usuario.documento;

    } catch (error) {
        alert("Erro ao recolher informações do Usuário!")
    }
    
}

// Abre o documento (PDF ou imagem) numa nova aba
function abrirDocumento() {
    if (!documentoSelecionado) {
        alert("Selecione uma conta antes de visualizar o documento.");
        return;
    }
    window.open(`http://localhost:3000/Docs/${documentoSelecionado}`, "_blank");
}
 
// Finaliza a avaliação da conta selecionada
async function avaliar() {
    if (!idUserSelecionado) {
        alert("Selecione uma conta antes de avaliar.");
        return;
    }
 
    const aprovar = confirm("Clique em OK para APROVAR a conta, ou Cancelar para REPROVAR.");
    const acao = aprovar ? "aprovar" : "reprovar";
    //Manda para o banco para pegar as infos
    const resposta = await fetch(`http://localhost:3000/Adm/solicitacoes/${idUserSelecionado}`);

     if (!resposta.ok) {
        throw new Error(`Erro HTTP: ${resposta.status}`);
    }

    //Expõe as infos
    const usuario = await resposta.json();
 
    try {
        if(acao === "aprovar"){
            const aprov = true;
            const resposta = await fetch(`http://localhost:3000/Adm/solicitacoes`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nome_completo: usuario.nome_completo,
                telefone: usuario.telefone,
                email: usuario.email,
                cpf: usuario.cpf,
                senha: usuario.senha,
                area_profissional: usuario.area_profissional,
                documento: usuario.documento,
                verificacao: aprov
            })
        
            });
            if (!resposta.ok) {
                alert("Erro na Aprovação");
            }
            window.location.reload();
            alert("Conta Aprovada!");
        }else if(acao === "reprovar"){
            const aprov = false;
            const resposta = await fetch(`http://localhost:3000/Adm/solicitacoes`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nome_completo: usuario.nome_completo,
                telefone: usuario.telefone,
                email: usuario.email,
                cpf: usuario.cpf,
                senha: usuario.senha,
                area_profissional: usuario.area_profissional,
                documento: usuario.documento,
                verificacao: aprov
            })
            });
            window.location.reload();
            if (!resposta.ok) {
                alert("Erro na Reprovação");
            }
            alert("Conta Suspensa!");
        }
 
        idUserSelecionado = null;
        documentoSelecionado = null;
        pegaContasNaoVerificadas();
 
    } catch (error) {
        console.error(error);
        alert("Erro ao avaliar a conta.");
    }
}

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", pegaContasNaoVerificadas);