package com.maace.connectEtec.dtos.usuario;

public record RespostaUsuarioDto (
        String login,
        String nomeCompleto,
        String nomeSocial,
        String tipoUsuario
)
{}
