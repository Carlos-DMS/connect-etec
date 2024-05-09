package com.maace.connectEtec.dtos;

public record AcessarPerfilUsuarioDto(
        String nomeCompleto,
        String nomeSocial,
        String nomeUsuario,
        String urlFotoPerfil,
        String sobre
) {}
