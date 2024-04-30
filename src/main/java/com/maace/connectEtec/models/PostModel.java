package com.maace.connectEtec.models;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "tb_post")
public class PostModel extends ComentarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPost;
    @ManyToMany
    @JoinTable(
            name = "tb_publicacao_usuario",
            joinColumns = @JoinColumn(name = "idPublicacao"),
            inverseJoinColumns = @JoinColumn(name = "enumTag")
    )
    private List<EnumTags> tags;
    @OneToMany
    @JoinTable(
            name = "tb_publicacao_usuario",
            joinColumns = @JoinColumn(name = "idPublicacao"),
            inverseJoinColumns = @JoinColumn(name = "idPublicacao")
    )
    protected List<ComentarioModel> respostas; // Verificar
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private GrupoModel idGrupo;
    @ManyToOne
    @JoinColumn(name = "idPerfil")
    private PerfilModel idPerfil

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
