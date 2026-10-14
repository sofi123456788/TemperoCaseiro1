let idReceitaSelecionado = null;
async function pegaReceitas() {
    const listaReceita = document.getElementById("listaReceitas");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/Adm/avaliarReceitas`);
        const rests = await resposta.json();

        console.log("Dados recebidos:", rests);

        listaReceita.innerHTML = '';

        for (let i = 0; i < rests.length; i++) {
            const receita = rests[i];

            console.log("Receita:", receita.titulo);
            console.log("Aprovada:", receita.aprovada);
            console.log("Tipo:", typeof receita.aprovada);

            if (!receita.aprovada) {
                const itemLI = document.createElement('li');
                itemLI.dataset.id = receita.id;
                itemLI.dataset.nome = receita.titulo;
                itemLI.textContent = `${receita.titulo}`;
                listaReceita.appendChild(itemLI);
            } else {
                console.log("Receita aprovada, não será exibida:", receita.titulo);
            }
        }
    } catch (error) {
        alert("Não foi possível encontrar as receitas.");
    }
}

async function pegarInfosReceita(event) {
    try {
        const idReceita = event.target.dataset.id;
        idReceitaSelecionado = idReceita;

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/Adm/avaliarReceitas/${idReceita}`);

        if (!resposta.ok) {
            throw new Error(`Erro HTTP: ${resposta.status}`);
        }

        //Expõe as infos
        const receita = await resposta.json();

        document.getElementById("titulo").textContent = receita.titulo;
        document.getElementById("categoria").textContent = receita.categoria;
        document.getElementById("ingredientes").textContent = receita.ingredientes;
        document.getElementById("modo_preparo").textContent = receita.modo_preparo;
        document.getElementById("tempo_preparo").textContent = receita.tempo_preparo;
        document.getElementById("rendimento").textContent = receita.rendimento;

    } catch (error) {
        alert("Erro ao recolher informações do Usuário!")
    }
    
}

// Finaliza a avaliação da receita selecionada
async function avaliar(event) {
    if (!idReceitaSelecionado) {
        alert("Selecione uma Receita antes de avaliar.");
        return;
    }
 
    const aprovar = confirm("Clique em OK para APROVAR a receita, ou Cancelar para REPROVAR.");
    const acao = aprovar ? "aprovar" : "reprovar";
    
    //Manda para o banco para pegar as infos
    const idReceita = event.target.dataset.id;
    const resposta = await fetch(`http://localhost:3000/Adm/avaliarReceitas/${idReceitaSelecionado}`);

     if (!resposta.ok) {
        throw new Error(`Erro HTTP: ${resposta.status}`);
    }

    //Expõe as infos
    const receita = await resposta.json();
 
    try {
        if(acao === "aprovar"){
            const aprov = true;
            const id = localStorage.getItem("id");
            const resposta = await fetch(`http://localhost:3000/Adm/avaliarReceitas/${idReceitaSelecionado}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                verificacao: aprov,
                idAprovador: id
            })
        
            });
            if (!resposta.ok) {
                alert("Erro na Aprovação");
            }
            pegaReceitas();
            window.location.reload();
            alert("Receita Aprovada!");
        }else if(acao === "reprovar"){
            const aprov = false;
            const id = localStorage.getItem("id");
            const resposta = await fetch(`http://localhost:3000/Adm/avaliarReceitas/${idReceitaSelecionado}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                titulo: receita.titulo,
                categoria: receita.categoria,
                ingredientes: receita.ingredientes,
                modo_preparo: receita.modo_preparo,
                tempo_preparo: receita.tempo_preparo,
                rendimento: receita.rendimento,
                idAdcionador: receita.usuario_id,
                aprovada: aprov,
                idDesaprovapor: id
            })
            });
            pegaReceitas();
            window.location.reload();
            if (!resposta.ok) {
                alert("Erro na Reprovação");
            }
            alert("Receita Suspensa!");
        }
 
        idReceitaSelecionado = null;
        pegaReceitas();
 
    } catch (error) {
        console.error(error);
        alert("Erro ao avaliar a Receita.");
    }
}

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", pegaReceitas);