package com.maace.connectEtec.dtos.perfilGrupo;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record EditarPerfilGrupoDto(
        @NotBlank String nome,
        @NullableNotBlank String urlFotoPerfil,
        @NullableNotBlank String sobre
) {}
