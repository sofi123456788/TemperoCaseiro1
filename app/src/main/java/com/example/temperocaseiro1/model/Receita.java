package com.example.temperocaseiro1.model;

// Classe responsável por representar uma receita no aplicativo.
// Ela funciona como um modelo dos dados que serão enviados para a API.
public class Receita {


    // Guarda o ID da receita.
    // Esse valor é gerado pelo banco de dados quando a receita é salva.
    private Integer id;


    // Guarda o título/nome da receita.
    private String titulo;


    // Guarda a categoria da receita.
    // Exemplo: Bolos, Doces, Salgados.
    private String categoria;


    // Guarda os ingredientes da receita.
    // Os vários ingredientes serão enviados como um único texto.
    private String ingredientes;


    // Guarda o modo de preparo da receita.
    // Contém os passos necessários para fazer a receita.
    private String modoPreparo;


    // Guarda o tempo necessário para preparar a receita.
    private String tempoPreparo;


    // Guarda a quantidade de porções/rendimento da receita.
    private String rendimento;


    // Guarda o ID do usuário que cadastrou a receita.
    // Esse valor será relacionado ao usuário que está logado.
    private Integer usuarioId;



    // Construtor usado para criar uma nova receita com os dados preenchidos.
    // O ID não é recebido porque será criado automaticamente pelo banco.
    public Receita(String titulo, String categoria, String ingredientes,
                   String modoPreparo, String tempoPreparo,
                   String rendimento, Integer usuarioId) {


        // Guarda o título recebido no atributo da classe.
        this.titulo = titulo;


        // Guarda a categoria recebida.
        this.categoria = categoria;


        // Guarda os ingredientes recebidos.
        this.ingredientes = ingredientes;


        // Guarda o modo de preparo recebido.
        this.modoPreparo = modoPreparo;


        // Guarda o tempo de preparo recebido.
        this.tempoPreparo = tempoPreparo;


        // Guarda o rendimento recebido.
        this.rendimento = rendimento;


        // Guarda o usuário responsável pela receita.
        this.usuarioId = usuarioId;
    }



    // Retorna o ID da receita.
    public Integer getId() {
        return id;
    }


    // Retorna o título da receita.
    public String getTitulo() {
        return titulo;
    }


    // Retorna a categoria da receita.
    public String getCategoria() {
        return categoria;
    }


    // Retorna os ingredientes da receita.
    public String getIngredientes() {
        return ingredientes;
    }


    // Retorna o modo de preparo da receita.
    public String getModoPreparo() {
        return modoPreparo;
    }


    // Retorna o tempo de preparo.
    public String getTempoPreparo() {
        return tempoPreparo;
    }


    // Retorna o rendimento da receita.
    public String getRendimento() {
        return rendimento;
    }


    // Retorna o ID do usuário que cadastrou a receita.
    public Integer getUsuarioId() {
        return usuarioId;
    }
}