package com.maace.connectEtec.dtos;

import com.maace.connectEtec.models.EnumNivelAcesso;
import jakarta.validation.constraints.NotBlank;

public record FuncionarioDto (
        @NotBlank String login,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        String nomeSocial,
        @NotBlank String cargo,
        @NotBlank String nivelAcesso){}
