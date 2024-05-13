package com.maace.connectEtec.dtos.perfilUsuario;

public record AcessarPerfilUsuarioDto(
        String nomeCompleto,
        String nomeSocial,
        String nomeUsuario,
        String urlFotoPerfil,
        String sobre
) {}
