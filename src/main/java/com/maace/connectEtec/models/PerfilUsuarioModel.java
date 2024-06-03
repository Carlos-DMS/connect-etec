package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_perfil_usuario")
public class PerfilUsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPerfil;
    private String urlFotoPerfil;
    private String sobre;
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_posts", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "post_id")
    private List<UUID> idPosts = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_posts_curtidos", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "post_curtido_id")
    private List<UUID> idPostsCurtidos = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_conexoes", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "conexao_id")
    private List<String> loginConexoes = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_grupos", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "grupo_id")
    private List<UUID> idGrupos = new ArrayList<>();


    public UUID getIdPerfil() {
        return idPerfil;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public void addIdPost(UUID idPost) {
        idPosts.add(idPost);
    }

    public List<UUID> getIdPostsCurtidos() {
        return idPostsCurtidos;
    }

    public void curtirPost(UUID idPost) {
        idPostsCurtidos.add(idPost);
    }

    public void removerCurtidaPost(UUID idPost) {
        idPostsCurtidos.remove(idPost);
    }

    public List<UUID> getIdPosts() {
        return idPosts;
    }

    public List<String> getLoginConexoes() {
        return loginConexoes;
    }

    public List<UUID> getGrupos() {
        return idGrupos;
    }
}