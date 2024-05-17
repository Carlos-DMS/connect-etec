package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record MudarSenhaDto(
        @NotBlank String senhaAntiga,
        @NotBlank String novaSenha
) {}
