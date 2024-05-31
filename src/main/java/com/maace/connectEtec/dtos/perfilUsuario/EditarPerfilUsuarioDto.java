package com.maace.connectEtec.dtos.perfilUsuario;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record EditarPerfilUsuarioDto(
        @NotBlank String nomeCompleto,
        @NullableNotBlank String nomeSocial,
        @NullableNotBlank String sobre
) {}
