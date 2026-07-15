const express = require("express");
const cors = require("cors");
const { Pool } = require("pg");

const app = express();

app.use(cors());
app.use(express.json());

//Infos. do BD
const pool = new Pool({
    user: "neondb_owner",
    host: "ep-late-art-acgy0zej.sa-east-1.aws.neon.tech",
    database: "neondb",
    password: "npg_Y0FCBzXtv6eG",
    port: 5432,
    ssl: {
    rejectUnauthorized: false
    }
});

pool.connect()
    .then(() => {
        console.log("Banco conectado!");
    })
    .catch((error) => {
        console.error("Erro ao conectar no banco:");
        console.error(error);
    });

//Fala se ta funcionando Top
app.listen(3000, () => {
    console.log("Servidor rodando!");
});

//Rota para cadastro
app.post("/cadastro", async (req, res) => {
    try {

        console.log("REQUISIÇÃO RECEBIDA");//As infos foram pegas bonitinhas
        console.log(req.body);

        const {nome_completo, telefone, email, cpf, senha, area_profissional} = req.body;

        //Mandando para o banco de dados
        await pool.query(
            `INSERT INTO profissionais(nome_completo, telefone, email, cpf, senha, area_profissional) 
            VALUES($1,$2,$3,$4,$5,$6)`,
            [nome_completo, telefone, email, cpf, senha, area_profissional]
        );

        console.log("Cadastro Feito");
        res.json({
            mensagem: "Cadastro realizado!"
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});

//Rota para o login
app.post("/login", async (req, res) => {
    try {
         const { email, senha } = req.body;

        // consulta no banco
        const resultado = await pool.query(
        "SELECT * FROM profissionais WHERE email = $1",
        [email]);

        //Guarda as infos
        const usuario = resultado.rows[0];//O 0 é por conta haver apenas 1 usuário com aquele email

        //Faz a verificação
        if (usuario.email !== email) {
            return res.status(401).send("Usuário não encontrado");
        }else if (usuario.senha !== senha) {
            return res.status(401).send("Senha incorreta");
        }

        res.json({
            mensagem: "Login realizado com sucesso!",
            id: usuario.id,
            area: usuario.area_profissional
        });

    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});

//Pega todas as infos do usuário para a página do perfil
app.get("/perfil/:email", async (req, res) => {
    try {
        const email = req.params.email;

        const resultado = await pool.query(
            "SELECT * FROM profissionais WHERE email = $1",
            [email]
        );

        //Guarda as infos "pessoais"
        const usuario = resultado.rows[0];//O 0 é por conta haver apenas 1 usuário com aquele email

        //Faz a verificação
        if (usuario.email !== email) {
            return res.status(401).send("Usuário não encontrado");
        }

        res.json(resultado.rows[0]);

    } catch (erro) {
        console.error(erro);
        res.status(500).send("Erro no servidor");
    }
});

//Rota para Deletar Conta
app.delete("/excluirConta/:id", async (req, res) => {

    const idpp = req.params.id;

    try {
        await pool.query("BEGIN");

        // Salva na tabela de excluídos
        await pool.query(
            "INSERT INTO exclusoes_cp (profissionaisId, excluido_em, motivo) VALUES ($1, NOW(), 'Conta removida pelo usuário')",
            [idpp]
        );

        // Remove da tabela profissionais
        await pool.query(
            "DELETE FROM profissionais WHERE id = $1",
            [idpp]
        );

        await pool.query("COMMIT");

        res.send("Conta excluída com sucesso!");

    } catch (erro) {
        await pool.query("ROLLBACK");
        console.error(erro);
        res.status(500).send("Erro ao excluir conta.");
    }
});

//Rota para Adicionar Restaurante
app.post("/addRestaurante", async (req, res) => {
    try {
        console.log("REQUISIÇÃO RECEBIDA");//As infos foram pegas bonitinhas
        console.log(req.body);

        const {nome, telefone, cep, rua, logradouro, bairro, cidade, estado, horaA, horaF} = req.body;

        //Mandando para o banco de dados
        await pool.query(
            `INSERT INTO restaurantes(nome, telefone, cep, rua, logradouro, bairro, cidade, estado, horario_abertura, horario_fechamento) 
            VALUES($1,$2,$3,$4,$5,$6,$7,$8,$9,$10)`,
            [nome, telefone, cep, rua, logradouro, bairro, cidade, estado, horaA, horaF]
        );

        //Avisa para o usuário que funcionou
        console.log("Restaurante Adicionado!");
        res.send("Restaurante Adcionado!");
        res.json({
            mensagem: "Restaurante Adcionado!"
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});

//Rota para Adicionar Centro de Apoio
app.post("/addCentroApoio", async (req, res) => {
    try {
        console.log("REQUISIÇÃO RECEBIDA");//As infos foram pegas bonitinhas
        console.log(req.body);

        const {nome, telefone, email, rua, logradouro, bairro, cidade, estado, horaA, horaF, tipo} = req.body;

        //Mandando para o banco de dados
        await pool.query(
            `INSERT INTO centroApoio(nome, telefone, email, rua, logradouro, bairro, cidade, estado, horario_abertura, horario_fechamento, tipo) 
            VALUES($1,$2,$3,$4,$5,$6,$7,$8,$9,$10, $11)`,
            [nome, telefone, email, rua, logradouro, bairro, cidade, estado, horaA, horaF, tipo]
        );

        //Avisa para o usuário que funcionou
        console.log("Centro Adicionado!");
        res.send("Centro Adicionado!");
        res.json({
            mensagem: "Centro Adcionado!"
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});

//Rota para Adicionar Canal de Apoio
app.post("/addCanal", async (req, res) => {
    try {
        console.log("REQUISIÇÃO RECEBIDA");//As infos foram pegas bonitinhas
        console.log(req.body);

        const {nome, telefone, email, rua, logradouro, bairro, cidade, estado, horaA, horaF, modo} = req.body;

        //Mandando para o banco de dados
        await pool.query(
            `INSERT INTO canalApoio(nome, telefone, email, rua, logradouro, bairro, cidade, estado, horario_abertura, horario_fechamento, modo) 
            VALUES($1,$2,$3,$4,$5,$6,$7,$8,$9,$10, $11)`,
            [nome, telefone, email, rua, logradouro, bairro, cidade, estado, horaA, horaF, modo]
        );

        //Avisa para o usuário que funcionou
        console.log("Canal Adicionado!");
        res.send("Canal Adicionado!");
        res.json({
            mensagem: "Canal Adcionado!"
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});

//Rota para Registrar Atendimentos
app.post("/registrarAtend", async (req, res) => {
    try {
        console.log("REQUISIÇÃO RECEBIDA");//As infos foram pegas bonitinhas
        console.log(req.body);

        const {nome, data, tempo, consideracoes, modo} = req.body;

        //Mandando para o banco de dados
        await pool.query(
            `INSERT INTO atendimento(nomepaciente, dataatendimento, duracao, consideracoes, modo) 
            VALUES($1,$2,$3,$4,$5)`,
            [nome, data, tempo, consideracoes, modo]
        );

        //Avisa para o usuário que funcionou
        console.log("Atendimento Registrado!");
        res.send("Atendimento Registrado!");
        res.json({
            mensagem: "Atendimento Registrado!"
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            mensagem: "Erro no servidor"
        });
    }

});