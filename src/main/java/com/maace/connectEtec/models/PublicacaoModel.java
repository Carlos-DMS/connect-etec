package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class PublicacaoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID idPublicacao;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    protected UsuarioModel idUsuario;
    protected String urlMidia;
    protected static Integer limiteCaracter = 2000;
    protected LocalDateTime momentoPublicacao = LocalDateTime.now();
    protected String conteudo;

    public UUID getIdPublicacao() {
        return idPublicacao;
    }

    public LocalDateTime getMomentoPublicacao() {
        return momentoPublicacao;
    }

    public UsuarioModel getAutor() {
        return autor;
    }

    public void setAutor(UsuarioModel autor) {
        this.autor = autor;
    }

    public String getUrlMidia() {
        return urlMidia;
    }

    public void setUrlMidia(String urlMidia) {
        this.urlMidia = urlMidia;
    }

    public Integer getLimiteCaracter() {
        return limiteCaracter;
    }

    public void setLimiteCaracter(Integer limiteCaracter) {
        this.limiteCaracter = limiteCaracter;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}