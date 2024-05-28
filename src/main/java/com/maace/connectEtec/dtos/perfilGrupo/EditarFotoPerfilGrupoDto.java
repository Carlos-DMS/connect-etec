package com.maace.connectEtec.dtos.perfilGrupo;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record EditarFotoPerfilGrupoDto(
        @NullableNotBlank String urlFotoPerfil,
        @NotBlank String idGrupo
) { }
