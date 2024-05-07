package com.maace.connectEtec.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_perfil_grupo")
public class PerfilGrupoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID idPerfil;
    protected String urlFotoPerfil;
    protected String sobre;
    protected List<UUID> idPosts;

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

}
