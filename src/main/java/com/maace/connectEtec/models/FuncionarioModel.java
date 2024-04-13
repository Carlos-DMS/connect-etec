package com.maace.connectEtec.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_funcionario")
public class FuncionarioModel extends UsuarioModel{
    private String cargo;
    private EnumNivelAcesso nivelAcesso;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public EnumNivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = EnumNivelAcesso.valueOf(nivelAcesso);
    }
}
