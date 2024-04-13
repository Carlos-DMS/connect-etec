package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record AlunoDto(
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        String nomeSocial,
        @NotBlank String curso) {}
