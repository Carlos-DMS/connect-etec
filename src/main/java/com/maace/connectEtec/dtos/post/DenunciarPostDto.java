package com.maace.connectEtec.dtos.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DenunciarPostDto(
        @NotBlank String idPost,
        @NotNull boolean estaDenunciado
) {}
