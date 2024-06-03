package com.maace.connectEtec.dtos.post;

import jakarta.validation.constraints.NotBlank;

public record IdPostDto(@NotBlank String idPost) {
}
