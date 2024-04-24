package com.maace.connectEtec.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_perfil_usuario")
public class PerfilUsuarioModel extends PerfilModel{
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel usuario;
    @ManyToMany
    @JoinTable(
            name = "tb_perfil_usuario_conexoes",
            joinColumns = @JoinColumn(name = "perfil_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioModel> conexoes;

    @ManyToMany(mappedBy = "perfis")
    private List<GrupoModel> grupos;

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioModel> getConexoes() {
        return conexoes;
    }

    public void setConexoes(List<UsuarioModel> conexoes) {
        this.conexoes = conexoes;
    }

    public List<GrupoModel> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoModel> grupos) {
        this.grupos = grupos;
    }
}
