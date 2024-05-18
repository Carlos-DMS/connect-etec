package com.maace.connectEtec.dtos;


import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioDto(
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        @NullableNotBlank String nomeSocial,
        @NotBlank String tipoUsuario,
        @NotBlank String idRequest,
        @NotNull Integer codigoDeValidacao
) {}
