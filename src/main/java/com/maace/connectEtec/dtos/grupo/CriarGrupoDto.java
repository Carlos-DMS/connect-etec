package com.maace.connectEtec.dtos.grupo;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record CriarGrupoDto(
        @NotBlank String nome,
        @NullableNotBlank String urlFotoPerfil,
        @NullableNotBlank String sobre
) { }
