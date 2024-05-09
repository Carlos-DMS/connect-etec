package com.maace.connectEtec.dtos;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record EditarPerfilDto(
        @NotBlank String nomeCompleto,
        @NullableNotBlank String nomeSocial,
        @NullableNotBlank String urlFotoPerfil,
        @NullableNotBlank String sobre
) { }
