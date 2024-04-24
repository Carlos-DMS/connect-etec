package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_post")
public class PostModel extends ComentarioModel {

    @ManyToMany
    @JoinTable(
            name = "tb_publicacao_usuario",
            joinColumns = @JoinColumn(name = "idPublicacao"),
            inverseJoinColumns = @JoinColumn(name = "enumTag")
    )
    private List<EnumTags> tags;
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private GrupoModel grupo;

    public List<EnumTags> getTags() {
        return tags;
    }

    public GrupoModel getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoModel grupo) {
        this.grupo = grupo;
    }
}
