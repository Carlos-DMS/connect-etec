package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String login) {}
