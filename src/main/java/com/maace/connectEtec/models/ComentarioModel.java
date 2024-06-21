package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Integer qtdLike = 0;

    public UUID getIdComentario() {
        return idComentario;
    }

    public LocalDateTime getMomentoPublicacao() {
        return momentoPublicacao;
    }

    public String momentoFormatado() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        return momentoPublicacao.minusHours(3).format(formato);
    }

    public String getLoginAutor() {
        return loginAutor;
    }

    public void setLoginAutor(String usuario) {
        this.loginAutor = usuario;
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

    public void curtir() {
        this.qtdLike += 1;
    }

    public void removerCurtida() {
        this.qtdLike -= 1;
    }
}