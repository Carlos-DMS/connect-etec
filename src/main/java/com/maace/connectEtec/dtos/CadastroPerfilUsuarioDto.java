package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record CadastroPerfilUsuarioDto(
        @NotBlank String urlFotoPerfil,
        @NotBlank String sobre
)
{}
