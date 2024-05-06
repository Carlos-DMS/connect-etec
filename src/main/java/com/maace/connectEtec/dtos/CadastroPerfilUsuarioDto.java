package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record CadastroPerfilUsuarioDto(
        @NotBlank String urlFotoPerfil,
        @NotBlank String urlBanner,
        @NotBlank String sobre,
        List<UUID> idGrupos
)
{}
