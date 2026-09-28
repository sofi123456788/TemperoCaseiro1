package com.example.temperocaseiro1.model;

// Classe responsável por representar uma receita no aplicativo.
// Ela funciona como um modelo dos dados enviados e recebidos pela API.
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
    private String ingredientes;

    // Guarda o modo de preparo da receita.
    private String modoPreparo;

    // Guarda o tempo necessário para preparar a receita.
    private String tempoPreparo;

    // Guarda a quantidade de porções/rendimento da receita.
    private String rendimento;

    // Guarda o ID do usuário que cadastrou a receita.
    private Integer usuarioId;

    // Informa se a receita foi aprovada.
    // false = aguardando aprovação
    // true = receita aprovada
    private Boolean aprovada;


    // Construtor vazio.
    // O Retrofit/Gson pode precisar dele para transformar
    // os dados recebidos da API em um objeto Receita.
    public Receita() {
    }


    // Construtor usado para criar uma nova receita.
    public Receita(String titulo, String categoria, String ingredientes,
                   String modoPreparo, String tempoPreparo,
                   String rendimento, Integer usuarioId) {

        this.titulo = titulo;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.usuarioId = usuarioId;

        // Toda receita nova começa aguardando aprovação.
        this.aprovada = false;
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


    // Retorna se a receita foi aprovada.
    public Boolean getAprovada() {
        return aprovada;
    }


    // Define se a receita foi aprovada.
    public void setAprovada(Boolean aprovada) {
        this.aprovada = aprovada;
    }
}