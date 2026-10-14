let idDenunciaSelecionado = null;
async function pegaDenuncia() {
    const listaDenuncia = document.getElementById("listaDenuncias");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/PsiAdv/denuncia`);
        const rests = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaDenuncia.innerHTML = '';
        for (let i = 0; i < rests.length; i++) {
            const denuncia = rests[i];
            const itemLI = document.createElement('li');
            itemLI.dataset.id = denuncia.id;
            itemLI.dataset.nome = denuncia.tipo_violencia;
            itemLI.textContent = `${denuncia.tipo_violencia}`;
            listaDenuncia.appendChild(itemLI);    
        }
    } catch (error) {
        alert("Não foi possível encontrar as denúncia.");
    }
}

async function pegarInfosDenuncia(event) {
    try {
        const idDenuncia = event.target.dataset.id;
        idDenunciaSelecionado = idDenuncia;

        //Manda para o banco para pegar as infos da denuncia 
        const resposta = await fetch(`http://localhost:3000/PsiAdv/denuncia/${idDenuncia}`);

        if (!resposta.ok) {
            throw new Error(`Erro HTTP: ${resposta.status}`);
        }

        //Expõe as infos
        const vitima = await resposta.json();
        const idVitima = vitima.usuario_id;

        //Manda para o banco para pegar as infos da vítima 
        const resposta2 = await fetch(`http://localhost:3000/PsiAdv/denuncia/${idVitima}`);

        if (!resposta2.ok) {
            throw new Error(`Erro HTTP: ${resposta2.status}`);
        }

        const vitimaNome = await resposta2.json();

        document.getElementById("nome").textContent = vitimaNome.nome_completo;
        document.getElementById("tipo_violencia").textContent = vitima.tipo_violencia;
        document.getElementById("relato").textContent = vitima.relato;
        document.getElementById("data_denuncia").textContent = vitima.data_denuncia;
        document.getElementById("status").textContent = vitima.status;

    } catch (error) {
        alert("Erro ao recolher informações da denúncia!")
    }
    
}

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", pegaDenuncia);