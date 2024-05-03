package com.maace.connectEtec.dtos;

public record RespostaUsuarioDto (
        String login,
        String nomeCompleto,
        String nomeSocial,
        String tipoUsuario
)
{}
