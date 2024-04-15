package com.maace.connectEtec.dtos;


import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record AlunoDto(
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        @NullableNotBlank String nomeSocial,
        @NotBlank String curso) {}
