package com.maace.connectEtec.dtos.usuario;

import jakarta.validation.constraints.NotBlank;

public record BuscarUsuarioDto (@NotBlank String login){ }
