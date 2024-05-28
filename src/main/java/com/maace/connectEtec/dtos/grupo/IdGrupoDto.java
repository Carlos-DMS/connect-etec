package com.maace.connectEtec.dtos.grupo;

import jakarta.validation.constraints.NotBlank;

public record IdGrupoDto(
        @NotBlank String id
) { }
