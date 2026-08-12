async function mostrarNomeCanal() {
    const listaCanal = document.getElementById("listaCanais");
    try {
        //Manda para o server o email do usuário para pegar as demais infos
        const resposta = await fetch(`http://localhost:3000/editarCanal/`);
        const canais = await resposta.json();

        // Limpa a lista antes de renderizar (evita duplicados se a função rodar de novo)
        listaUL.innerHTML = '';
        for (let i = 0; i < canais.length; i++) {
            const nomeC= canais[i];
            const itemLI = document.createElement('li');
            itemLI.textContent = '${canal.nome}';
            listaCanal.appendChild(itemLI);
        }
    } catch (error) {
        
    }
}