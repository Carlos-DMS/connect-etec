package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_grupo")
public class GrupoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idGrupo;
    @Column(unique = true)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "idDono")
    private UsuarioModel dono;
    @ManyToMany
    @JoinTable(
        name = "tb_grupo_usuario", 
        joinColumns = @JoinColumn(name = "grupo_id"), 
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<UsuarioModel> usuarios;
    @ManyToMany
    @JoinTable(
        name = "tb_grupo_administrador", 
        joinColumns = @JoinColumn(name = "grupo_id"), 
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
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

    public UsuarioModel getLoginDono() {
        return dono;
    }

    public void setLoginDono(UsuarioModel dono) {
        this.dono = dono;
    }

    public List<UsuarioModel> getLoginUsuarios() {
        return usuarios;
    }

    public void addUsuario(UsuarioModel usuario) {
        this.usuarios.add(usuario);
    }

    public List<UsuarioModel> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<UsuarioModel> administradores) {
        this.administradores = administradores;
    }
}
