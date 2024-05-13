package com.maace.connectEtec.dtos.usuario;


import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioDto(
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        @NullableNotBlank String nomeSocial,
        @NotBlank String tipoUsuario
)
{}
