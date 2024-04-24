package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@MappedSuperclass
@Table(name = "tb_comentario")
public class ComentarioModel extends PublicacaoModel{
    protected Integer qtdLike;
    @OneToMany  // Caso dê erro neste quesito, mappedby é o problema mais provavel
    @JoinTable(
            name = "tb_publicacao_usuario",
            joinColumns = @JoinColumn(name = "idPublicacao"),
            inverseJoinColumns = @JoinColumn(name = "idPublicacao")
    )
    protected List<ComentarioModel> respostas;

    public Integer getQtdLike() {
        return qtdLike;
    }

    public void setQtdLike(Integer qtdLike) {
        this.qtdLike = qtdLike;
    }

    public List<ComentarioModel> getRespostas() {
        return respostas;
    }

}
