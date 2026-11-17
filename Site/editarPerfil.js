async function pegarInfosPerfil() {
    try {
        const idPerfil = localStorage.getItem("id");

        //Manda para o banco para pegar as infos
        const resposta = await fetch(`http://localhost:3000/editarPerfil/pegainfos/${idPerfil}`);
        const infoPerfil = await resposta.json();

        //Mostra as infos para o profissional
        document.getElementById("nomePerfil").value = infoPerfil.nome_completo;
        document.getElementById("telefonePerfil").value = infoPerfil.telefone;
        document.getElementById("emailPerfil").value = infoPerfil.email;
    
    } catch (error) {
        alert("Erro ao recolher informações do Perfil!")
    }
    
}

async function alterarInfosPerfil() {
    const idPerfil = localStorage.getItem("id");
    event.preventDefault();

    //Pega as infos nos campos
    const nomeNovo = document.getElementById("nomePerfil").value;
    const telefoneNovo = document.getElementById("telefonePerfil").value;
    const emailNovo = document.getElementById("emailPerfil").value;

    //Envia as alterações pára o banco
    try {
        console.log("Enviando dados...");
        //Manda as infos coletadas para o server
        const idPerfil = localStorage.getItem("id");
        const resposta = await fetch(`http://localhost:3000/editarPerfil/${idPerfil}`,{
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({nomeNovo, telefoneNovo, emailNovo})
            }
        );
        const texto = await resposta.text();

        console.log(texto);

        //Confirma que deu tudo certo
        if (resposta.ok) {
            alert("Perfil alterado com sucesso!");
            //Manda para o banco para pegar as infos
            const resposta = await fetch(`http://localhost:3000/editarPerfil/pegainfos/${idPerfil}`);
            const infoPerfil = await resposta.json();
            if(infoPerfil.area_profissional === "Psicologia" || infoPerfil.area_profissional === "Advocacia"){
                window.location.href = "perfil.html";
            }else{
                window.location.href = "perfilA.html";
            }
            
        }
    } catch (erro) {
        console.error("Erro:", erro);
        alert("Não foi possível realizar essas alterações!");
    }
}

document.addEventListener("DOMContentLoaded", pegarInfosPerfil);