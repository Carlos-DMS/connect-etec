package com.maace.connectEtec.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record curtirPostDto(@NotNull boolean estaCurtido, @NotBlank String idPost) {
}
