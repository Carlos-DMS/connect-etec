package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="tb_comentario")
public class ComentarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idComentario;
    private String loginAutor;
    private String urlMidia;
    private final LocalDateTime momentoPublicacao = LocalDateTime.now();
    private String conteudo;
    private Integer qtdLike;

    public UUID getIdComentario() {
        return idComentario;
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
}