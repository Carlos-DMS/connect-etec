package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record RespostaPerfilUsuarioDto(
        @NotBlank UUID idPerfil,
        @NotBlank String urlFotoPerfil,
        @NotBlank String sobre
) {}