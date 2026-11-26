const CORES_PIZZA = ['#4b0050', '#8a0a5e', '#b8305a', '#d86b8a', '#e9a3b8'];

let graficoPizza = null;
let graficoLinha = null;

async function carregarRelatorio(periodo) {
    try {
        const area = localStorage.getItem("area");

        if(area === "Psicologia" || area === "Advocacia"){
            //Manda para o server o período para pegar as infos
            const resposta = await fetch(`http://localhost:3000/PsiAdv/relatorios?periodo=${periodo}`);

            if (!resposta.ok) {
                throw new Error(`Erro HTTP: ${resposta.status}`);
            }

            const dados = await resposta.json();

            mostrarCards(dados);
            desenharPizza(dados.porCategoria);
            desenharLinha(dados.serie, dados.agrupamento);

        }else if(area === "Administração"){
            //Manda para o server o período para pegar as infos
            const resposta = await fetch(`http://localhost:3000/Adm/relatorios?periodo=${periodo}`);

            if (!resposta.ok) {
                throw new Error(`Erro HTTP: ${resposta.status}`);
            }

            const dados = await resposta.json();

            mostrarCardsA(dados);
            desenharPizza(dados.porCategoria);
            desenharLinha(dados.serie, dados.agrupamento, "Mulheres cadastradas");
        }
        

    } catch (error) {
        console.error(error);
        alert("Erro ao carregar os relatórios!");
    }
}

// ---------- Cards ----------
function formatarTempo(min) {
    if (!min) return "0min";
    const h = Math.floor(min / 60);
    const m = min % 60;
    if (h === 0) return `${m}min`;
    return m === 0 ? `${h}h` : `${h}h ${m}min`;
}

function mostrarCards(dados) {
    document.getElementById("cardTotal").textContent = dados.total;
    document.getElementById("cardTempo").textContent = formatarTempo(dados.tempoMedioMin);
    document.getElementById("cardPacientes").textContent = dados.pacientes;
}

function mostrarCardsA(dados) {
    document.getElementById("cardRest").textContent = dados.restaurantes;
    document.getElementById("cardProf").textContent = dados.profissionais;
    document.getElementById("cardMulher").textContent = dados.usuarios;
}

// ---------- Pizza ----------
function desenharPizza(categorias) {
    const labels = categorias.map(c => c.categoria);
    const valores = categorias.map(c => c.total);
    const total = valores.reduce((soma, v) => soma + v, 0);
    const cores = CORES_PIZZA.slice(0, categorias.length);

    // Legenda (igual ao layout)
    document.getElementById("legendaPizza").innerHTML = labels
        .map((l, i) => `<li><i style="background:${cores[i]}"></i>${l}</li>`)
        .join("");

    if (graficoPizza) graficoPizza.destroy();

    graficoPizza = new Chart(document.getElementById("graficoPizza"), {
        type: "pie",
        data: { labels, datasets: [{ data: valores, backgroundColor: cores, borderWidth: 0 }] },
        options: {
            maintainAspectRatio: false,
            layout: { padding: 28 },
            plugins: { legend: { display: false } }
        },
        plugins: [{
            // Escreve a porcentagem fora de cada fatia
            id: "percentuais",
            afterDatasetsDraw(chart) {
                if (!total) return;
                const ctx = chart.ctx;
                ctx.save();
                ctx.font = "bold 11px sans-serif";
                ctx.fillStyle = "#000";
                ctx.textAlign = "center";
                ctx.textBaseline = "middle";
                chart.getDatasetMeta(0).data.forEach((arco, i) => {
                    const ang = (arco.startAngle + arco.endAngle) / 2;
                    const r = arco.outerRadius + 22;
                    ctx.fillText(`${(valores[i] / total * 100).toFixed(1)}%`,
                        arco.x + Math.cos(ang) * r, arco.y + Math.sin(ang) * r);
                });
                ctx.restore();
            }
        }]
    });
}

// ---------- Linha ----------
function rotuloPonto(iso, agrupamento) {
    const d = new Date(iso);
    if (agrupamento === "month") return d.toLocaleDateString("pt-BR", { month: "short", timeZone: "UTC" });
    if (agrupamento === "week") return d.toLocaleDateString("pt-BR", { day: "2-digit", month: "2-digit", timeZone: "UTC" });
    return String(d.getUTCDate()); // dia
}

function desenharLinha(serie, agrupamento) {
    const labels = serie.map(p => rotuloPonto(p.ponto, agrupamento));
    const valores = serie.map(p => p.total);

    if (graficoLinha) graficoLinha.destroy();

    graficoLinha = new Chart(document.getElementById("graficoLinha"), {
        type: "line",
        data: {
            labels,
            datasets: [{
                label: "Nº de Atendimentos",
                data: valores,
                borderColor: "#00bfff",
                backgroundColor: "#00bfff",
                borderWidth: 1.5,
                pointRadius: 2,
                tension: 0.4
            }]
        },
        options: {
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, ticks: { precision: 0 }, grid: { color: "#eee" } },
                x: { grid: { display: false } }
            }
        }
    });
}

// Troca o período quando o usuário muda o filtro
document.querySelectorAll('input[name="periodo"]').forEach(radio => {
    radio.addEventListener("change", () => carregarRelatorio(radio.value));
});

//Roda a função automáticamente
document.addEventListener("DOMContentLoaded", () => carregarRelatorio("mes"));