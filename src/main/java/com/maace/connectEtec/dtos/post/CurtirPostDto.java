package com.maace.connectEtec.dtos.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CurtirPostDto(@NotNull boolean estaCurtido, @NotBlank String idPost) {
}
