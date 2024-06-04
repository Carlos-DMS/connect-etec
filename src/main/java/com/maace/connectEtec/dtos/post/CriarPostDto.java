package com.maace.connectEtec.dtos.post;

import com.maace.connectEtec.annotations.NullableNotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarPostDto(
        @NullableNotBlank String urlMidia,
        @NullableNotBlank String conteudo,
        @NullableNotBlank String idGrupo,
        @NotNull String tag
) {}
