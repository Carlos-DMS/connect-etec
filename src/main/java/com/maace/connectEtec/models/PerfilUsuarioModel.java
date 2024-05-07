package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_perfil_usuario")
public class PerfilUsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPerfil;
    private String urlFotoPerfil;
    private String sobre;
    private List<UUID> idPosts;
    private List<String> loginConexoes;
    private List<UUID> idGrupos;

    public UUID getIdPerfil() {
        return idPerfil;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public List<UUID> getIdPosts() {
        return idPosts;
    }

    public List<String> getLoginConexoes() {
        return loginConexoes;
    }

    public List<UUID> getGrupos() {
        return idGrupos;
    }
}
