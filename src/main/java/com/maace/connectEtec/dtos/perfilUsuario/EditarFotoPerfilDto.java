package com.maace.connectEtec.dtos.perfilUsuario;

import jakarta.validation.constraints.NotBlank;

public record EditarFotoPerfilDto(@NotBlank String urlFotoPerfil) {}
