package com.maace.connectEtec.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_usuario")
public class UsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Email
    @Column(unique = true)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    private String nomeCompleto;
    private String nomeSocial;
    private EnumTipoUsuario tipoUsuario;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public EnumTipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = EnumTipoUsuario.valueOf(tipoUsuario);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioModel that = (UsuarioModel) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }
}
