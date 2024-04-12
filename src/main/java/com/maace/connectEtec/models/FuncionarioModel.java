package com.maace.connectEtec.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_funcionario")
public class FuncionarioModel extends UsuarioModel{
    private String cargo;
    private enumNivelAcesso nivelAcesso;
}
