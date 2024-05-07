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
    private UUID idPerfilGrupo;
    private String loginDono;
    private List<String> loginUsuarios;
    private List<String> loginAdmins;

    public UUID getIdGrupo() {
        return idGrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getIdPerfilGrupo() {
        return idPerfilGrupo;
    }

    public void setIdPerfilGrupo(UUID idPerfilGrupo) {
        this.idPerfilGrupo = idPerfilGrupo;
    }

    public String getLoginDono() {
        return loginDono;
    }

    public void setLoginDono(String loginDono) {
        this.loginDono = loginDono;
    }

    public List<String> getLoginUsuarios() {
        return loginUsuarios;
    }

    public List<String> getLoginAdmins() {
        return loginAdmins;
    }
}
