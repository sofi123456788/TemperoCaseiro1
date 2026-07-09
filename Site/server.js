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