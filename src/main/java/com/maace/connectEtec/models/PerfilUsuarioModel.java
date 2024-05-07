package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_perfil_usuario")
public class PerfilUsuarioModel extends PerfilModel{
    private List<String> loginConexoes;
    private List<UUID> idGrupos;

    public List<String> getLoginConexoes() {
        return loginConexoes;
    }

    public List<UUID> getGrupos() {
        return idGrupos;
    }
}
