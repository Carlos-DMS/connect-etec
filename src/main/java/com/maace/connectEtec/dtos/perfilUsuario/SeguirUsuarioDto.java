package com.maace.connectEtec.dtos.perfilUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SeguirUsuarioDto(
        @NotBlank String loginUsuarioSeguido,
        @NotNull boolean estaSeguido
) {}
