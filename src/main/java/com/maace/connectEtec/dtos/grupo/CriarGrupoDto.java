package com.maace.connectEtec.dtos.grupo;

import jakarta.validation.constraints.NotBlank;

public record CriarGrupoDto(
        @NotBlank String nome
) { }
