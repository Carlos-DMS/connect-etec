package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_perfil_grupo")
public class PerfilGrupoModel extends PerfilModel{
    private UUID idGrupo;

    public UUID getGrupo() {
        return idGrupo;
    }

    public void setGrupo(UUID idGrupo) {
        this.idGrupo = idGrupo;
    }
}
