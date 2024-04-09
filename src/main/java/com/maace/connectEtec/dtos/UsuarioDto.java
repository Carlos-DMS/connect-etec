package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(@NotBlank String login, @NotBlank String senha, @NotNull String nomeCompleto, String nomeSocial) {
}
