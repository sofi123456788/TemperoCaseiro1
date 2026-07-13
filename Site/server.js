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

        console.log("REQUISIÇÃO RECEBIDA");
        console.log(req.body);

        const {
            nome_completo,
            telefone,
            email,
            cpf,
            senha,
            area_profissional
        } = req.body;

        await pool.query(
            `INSERT INTO psicologos(
                nome_completo,
                telefone,
                email,
                cpf,
                senha,
                area_profissional) VALUES($1,$2,$3,$4,$5,$6)
            `,
            [
                nome_completo,
                telefone,
                email,
                cpf,
                senha,
                area_profissional
            ]
        );

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
        "SELECT * FROM psicologos WHERE email = $1",
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
            id: usuario.id
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
            "SELECT * FROM psicologos WHERE email = $1",
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

//Rota para Deletar
app.delete("/excluirConta/:id", async (req, res) => {

    const id = req.params.id;

    try {
        await pool.query("BEGIN");

        // Salva o id na tabela de excluídos
        await pool.query(
            "INSERT INTO excluidos (usuario_id, motivo, excluido_em) VALUES ($1, 'Conta removida pelo usuário', NOW())",
            [id]
        );

        // Remove da tabela psicologos
        await pool.query(
            "DELETE FROM psicologos WHERE id = $1",
            [id]
        );

        await pool.query("COMMIT");

        res.send("Conta excluída com sucesso!");

    } catch (erro) {
        await pool.query("ROLLBACK");
        console.error(erro);
        res.status(500).send("Erro ao excluir conta.");
    }
});