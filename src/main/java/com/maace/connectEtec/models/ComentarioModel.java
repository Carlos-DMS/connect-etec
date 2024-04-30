package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@MappedSuperclass
@Table(name = "tb_comentario")
public class ComentarioModel extends PublicacaoModel{
    protected Integer qtdLike;


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
