package com.maace.connectEtec.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MudarSenhaPorRequestDto(
        @NotBlank String idRequest,
        @NotNull Integer numeroDeRecuperacao,
        @NotBlank String login,
        @NotBlank String senha
) {}
