package com.maace.connectEtec.models;

public enum EnumTags {
    DESENVOLVIMENTO_DE_SISTEMAS("Desenvolvimento de sistemas"),
    RECURSOS_HUMANOS("Recursos humanos"),
    CONTABILIDADE("Contabilidade"),
    SEGURANCA_DO_TRABALHO("Segurança do trabalho");

    private final String tag;

    EnumTags(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
