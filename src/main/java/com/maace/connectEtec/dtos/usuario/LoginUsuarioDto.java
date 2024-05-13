package com.maace.connectEtec.dtos.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioDto(
        @NotBlank String login,
        @NotBlank String senha
)
{}
