package com.maace.connectEtec.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_perfil_grupos")
public class PerfilGrupoModel extends PerfilModel{
    @ManyToMany
    @JoinColumn(name = "idGrupos")
    private GrupoModel grupos;

    public GrupoModel getGrupos() {
        return grupos;
    }

    public void setGrupos(GrupoModel grupos) {
        this.grupos = grupos;
    }
}
