package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record RecuperarContaDto(@NotBlank String login) {}
