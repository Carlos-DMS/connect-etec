package com.maace.connectEtec.dtos.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CurtirComentarioDto(
        @NotBlank String idComentario,
        @NotNull Boolean estaCurtido
) {}
