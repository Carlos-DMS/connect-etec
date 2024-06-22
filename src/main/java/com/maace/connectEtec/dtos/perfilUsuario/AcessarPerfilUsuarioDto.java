package com.maace.connectEtec.dtos.perfilUsuario;

public record AcessarPerfilUsuarioDto(
        String nomeCompleto,
        String nomeSocial,
        String nomeUsuario,
        String urlFotoPerfil,
        String sobre,
        Integer qtdUsuariosSeguidos,
        Integer qtdSeguidores,
        Boolean estaSeguido,
        Boolean usuarioADM
) {}
