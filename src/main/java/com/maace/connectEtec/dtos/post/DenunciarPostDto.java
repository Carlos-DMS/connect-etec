package com.maace.connectEtec.dtos.post;

import jakarta.validation.constraints.NotBlank;

public record DenunciarPostDto(
        @NotBlank String idPost
) {}
