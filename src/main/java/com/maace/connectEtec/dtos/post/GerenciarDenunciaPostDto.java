package com.maace.connectEtec.dtos.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GerenciarDenunciaPostDto(
        @NotBlank String idPost,
        @NotNull Integer blockPost
) {}
