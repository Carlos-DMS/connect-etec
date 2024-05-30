package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_post")
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
    private Integer qtdLike = 0;
    private EnumTag tag;
    private List<UUID> idRespostas;
    private UUID idGrupo;

    public UUID getIdPost() {
        return idPost;
    }

    public LocalDateTime getMomentoPublicacao() {
        return momentoPublicacao;
    }

    public String momentoFormatado() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        return momentoPublicacao.format(formato);
    }

    public String getLoginAutor() {
        return loginAutor;
    }

    public void setLoginAutor(String login) {
        this.loginAutor = login;
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

    public void darLike() {
        this.qtdLike += 1;
    }

    public void removerLike() {
        this.qtdLike -= 1;
    }

    public void setTag(String tag) {
        this.tag = EnumTag.valueOf(tag);
    }

    public EnumTag getTag() {
        return tag;
    }

    public String getTagRelatorio() {
        return tag.getTag();
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
