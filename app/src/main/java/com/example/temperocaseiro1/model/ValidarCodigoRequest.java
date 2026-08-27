package com.example.temperocaseiro1.model;

public class ValidarCodigoRequest {

    private String emailCod;
    private String codigo;

    public ValidarCodigoRequest (String emailCod, String codigo ){
        this.emailCod = emailCod;
        this.codigo = codigo;

    }

    public String getEmailCod() {
        return emailCod;
    }

    public void setEmailCod(String emailCod ) {
        this.emailCod = emailCod;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
