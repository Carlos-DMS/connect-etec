package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record BuscarUsuarioDto (@NotBlank String login){ }
