package com.maace.connectEtec.dtos.perfilGrupo;

import jakarta.validation.constraints.NotBlank;

public record AtualizarPerfilGrupoDto(
        @NotBlank String urlFotoPerfil,
        @NotBlank String sobre
) {}
