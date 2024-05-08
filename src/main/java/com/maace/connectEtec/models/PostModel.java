package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="tb_post")
public class PostModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPost;
    private String loginAutor;
    private String urlMidia;
    private final LocalDateTime momentoPublicacao = LocalDateTime.now();
    private String conteudo;
    private Integer qtdLike;
    private List<EnumTags> tags;
    private List<UUID> idRespostas;
    private UUID idGrupo;

    public UUID getIdPost() {
        return idPost;
    }

    public LocalDateTime getMomentoPublicacao() {
        return momentoPublicacao;
    }

    public String getLoginAutor() {
        return loginAutor;
    }

    public void setLoginAutor(UsuarioModel usuario) {
        this.loginAutor = usuario.getLogin();
    }

    public String getUrlMidia() {
        return urlMidia;
    }

    public void setUrlMidia(String urlMidia) {
        this.urlMidia = urlMidia;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getQtdLike() {
        return qtdLike;
    }

    public void setQtdLike(Integer qtdLike) {
        this.qtdLike = qtdLike;
    }

    public List<EnumTags> getTags() {
        return tags;
    }

    public List<String> getTagsRelatorio() {
        List<String> tagsRelatorio = new ArrayList<>();

        for (EnumTags tag : tags){
            tagsRelatorio.add(tag.getTag());
        }

        return tagsRelatorio;
    }

    public List<UUID> getIdRespostas() {
        return idRespostas;
    }

    public UUID getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(UUID idGrupo) {
        this.idGrupo = idGrupo;
    }
}
