package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name="tb_request_recuperacao_senha")
public class RequestRecuperacaoSenhaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idRequest;
    private String login;
    private final Integer codigoDeRecuperacao = new Random().nextInt(900000) + 100000;
    private final LocalDateTime momento = LocalDateTime.now();

    public RequestRecuperacaoSenhaModel() {
    }

    public RequestRecuperacaoSenhaModel(String login) {
        this.login = login;
    }

    public UUID getIdRequest() {
        return idRequest;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getCodigoDeRecuperacao() {
        return codigoDeRecuperacao;
    }

    public LocalDateTime getMomento() {
        return momento;
    }
}
