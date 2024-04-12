package com.maace.connectEtec.models;

public enum enumNivelAcesso {

    BAIXO ("baixo"),
    MEDIO("médio"),
    ALTO("alto");

    private String enumRelatorio;

    enumNivelAcesso(String enumRelatorio) {
        this.enumRelatorio = enumRelatorio;
    }

    public String getEnumRelatorio() {
        return enumRelatorio;
    }
}
