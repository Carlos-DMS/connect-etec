package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record RespostaPerfilUsuarioDto(
        @NotBlank String nomePerfilUsuario,
        @NotBlank String urlFotoPerfil
) {}