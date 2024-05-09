package com.maace.connectEtec.models;

public enum EnumTag {
    DESENVOLVIMENTO_DE_SISTEMAS("Desenvolvimento de sistemas"),
    RECURSOS_HUMANOS("Recursos humanos"),
    CONTABILIDADE("Contabilidade"),
    SEGURANCA_DO_TRABALHO("Segurança do trabalho"),
    NOTICIA("Notícia"),
    EVENTO("Evento"),
    OUTRO("Outro");

    private final String tag;

    EnumTag(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
