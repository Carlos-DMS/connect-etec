package com.maace.connectEtec.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AlunoRequestDto(@NotBlank String login, @NotBlank String senha, @NotBlank String nomeCompleto, String nomeSocial, @NotBlank String curso) {
}
