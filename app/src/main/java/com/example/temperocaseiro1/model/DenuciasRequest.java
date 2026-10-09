package com.example.temperocaseiro1.model;

public class DenuciasRequest {

     private String usuarioDenuncia;
     private String tipoViolencia;
     private String relatoViolencia;


     public DenuciasRequest(String tipoViolencia, String relatoViolencia) {

     }

        public String getUsuarioDenuncia() {
            return usuarioDenuncia;
        }

        public void setUsuarioDenuncia(String usuarioDenuncia) {
            this.usuarioDenuncia = usuarioDenuncia;
        }

        public String getTipoViolencia() {
            return tipoViolencia;
        }

        public void setTipoViolencia(String tipoViolencia){
            this.tipoViolencia = tipoViolencia;
        }

        public String getRelatoViolencia() {
            return relatoViolencia;
        }

        public void setRelatoViolencia(String relatoViolencia) {
            this.relatoViolencia= relatoViolencia;
        }


    }



