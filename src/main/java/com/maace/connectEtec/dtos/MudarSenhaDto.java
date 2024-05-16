package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record MudarSenhaDto(
        @NotBlank String login,
        @NotBlank String senha
) {}
