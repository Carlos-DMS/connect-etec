package com.maace.connectEtec.dtos.comentario;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotBlank;


public record CriarComentarioDto(
        @NotBlank String idPost,
        @NullableNotBlank String conteudo,
        @NullableNotBlank String urlMidia
) {}
