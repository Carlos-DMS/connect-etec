package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MudarSenhaDto(
        @NotBlank String idRequest,
        @NotNull Integer numeroDeRecuperacao,
        @NotBlank String login,
        @NotBlank String senha
) {}
