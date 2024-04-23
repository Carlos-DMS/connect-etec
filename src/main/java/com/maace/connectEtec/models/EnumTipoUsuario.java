package com.maace.connectEtec.models;

public enum EnumTipoUsuario {
    USUARIO("usuário"),
    ADMINISTRADOR("administrador");

    private final String role;

    EnumTipoUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
