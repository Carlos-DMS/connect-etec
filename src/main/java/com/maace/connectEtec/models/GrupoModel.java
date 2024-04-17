package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name="tb_grupo")
public class GrupoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idGrupo;
    @Column(unique = true)
    private String nome;
    private UsuarioModel dono;
    private List<UsuarioModel> usuarios;
    private List<UsuarioModel> administradores;

    public UUID getIdGrupo() {
        return idGrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UsuarioModel getDono() {
        return dono;
    }

    public void setDono(UsuarioModel dono) {
        this.dono = dono;
    }

    public List<UsuarioModel> getUsuarios() {
        return usuarios;
    }

    public void addUsuario(UsuarioModel usuario) {
        this.usuarios = usuarios;
    }

    public List<UsuarioModel> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<UsuarioModel> administradores) {
        this.administradores = administradores;
    }
}
