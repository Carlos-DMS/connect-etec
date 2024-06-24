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
    @CollectionTable(name = "tb_perfil_usuario_posts_denunciados", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "post_denunciados_id")
    private List<UUID> idPostsDenunciados = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_comentarios_curtidos", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "comentario_curtido_id")
    private List<UUID> idComentariosCurtidos = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_usuarios_seguidos", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "usuario_seguido_login")
    private List<String> loginUsuariosSeguidos = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "tb_perfil_usuario_seguidores", joinColumns = @JoinColumn(name = "perfil_usuario_id"))
    @Column(name = "seguidor_login")
    private List<String> loginSeguidores = new ArrayList<>();
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

    public List<UUID> getIdPostsDenunciados() {
        return idPostsDenunciados;
    }

    public void curtirPost(UUID idPost) {
        idPostsCurtidos.add(idPost);
    }

    public void denunciarPost(UUID idPost) {
        idPostsDenunciados.add(idPost);
    }
    public void removerDenunciaPost(UUID idPost) {
        idPostsDenunciados.remove(idPost);
    }

    public void removerCurtidaPost(UUID idPost) {
        idPostsCurtidos.remove(idPost);
    }

    public List<UUID> getIdComentariosCurtidos() {
        return idComentariosCurtidos;
    }

    public void curtirComentario(UUID idComentario) {
        idComentariosCurtidos.add(idComentario);
    }

    public void removerCurtidaComentario(UUID idComentario) {
        idComentariosCurtidos.remove(idComentario);
    }

    public List<UUID> getIdPosts() {
        return idPosts;
    }

    public List<String> getLoginUsuariosSeguidos() {
        return loginUsuariosSeguidos;
    }

    public void adicionarLoginUsuarioSeguido(String loginUsuarioSeguido) {
        loginUsuariosSeguidos.add(loginUsuarioSeguido);
    }

    public void removerLoginUsuarioSeguido(String loginUsuarioSeguido) {
        loginUsuariosSeguidos.remove(loginUsuarioSeguido);
    }

    public int getQtdUsuariosSeguidos() {
        return loginUsuariosSeguidos.size();
    }

    public List<String> getLoginSeguidores() {
        return loginSeguidores;
    }

    public void adicionarLoginSeguidor(String loginSeguidor) {
        loginSeguidores.add(loginSeguidor);
    }

    public void removerLoginSeguidor(String loginSeguidor) {
        loginSeguidores.remove(loginSeguidor);
    }

    public int getQtdSeguidores() {
        return loginSeguidores.size();
    }

    public List<UUID> getGrupos() {
        return idGrupos;
    }
}