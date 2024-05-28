package com.maace.connectEtec.dtos.perfilGrupo;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record EditarDadosPerfilGrupoDto(
        @NotBlank String nome,
        @NullableNotBlank String sobre,
        @NotBlank String idGrupo
) {}
