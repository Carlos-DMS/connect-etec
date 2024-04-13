package com.maace.connectEtec.models;

public enum EnumNivelAcesso {

    BAIXO ("baixo"),
    MEDIO("médio"),
    ALTO("alto");

    private String enumRelatorio;

    EnumNivelAcesso(String enumRelatorio) {
        this.enumRelatorio = enumRelatorio;
    }

    public String getEnumRelatorio() {
        return enumRelatorio;
    }
}
