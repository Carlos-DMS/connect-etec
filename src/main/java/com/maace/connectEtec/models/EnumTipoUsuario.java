package com.maace.connectEtec.models;

public enum EnumTipoUsuario {
    ALUNO("aluno"),
    FUNCIONARIO("funcionário"),
    PROFESSOR("professor"),
    CORPO_DOCENTE("corpo docente");

    private final String enumRelatorio;

    EnumTipoUsuario(String enumRelatorio) {
        this.enumRelatorio = enumRelatorio;
    }
}
