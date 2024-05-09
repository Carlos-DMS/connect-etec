package com.maace.connectEtec.dtos;

import com.maace.connectEtec.annotations.NotEmptyList;
import com.maace.connectEtec.annotations.NullableNotBlank;

import java.util.List;

public record CriarPostDto(
        @NullableNotBlank String urlMidia,
        @NullableNotBlank String conteudo,
        @NullableNotBlank String idGrupo,
        @NotEmptyList List<String> tags
) { }
