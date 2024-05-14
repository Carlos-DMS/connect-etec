package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record EditarFotoPerfilDto(@NotBlank String urlFotoPerfil) {}
