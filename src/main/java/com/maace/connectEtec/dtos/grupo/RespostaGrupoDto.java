package com.maace.connectEtec.dtos.grupo;

import java.util.UUID;

public record RespostaGrupoDto(
        UUID idGrupo,
        String nome,
        String urlFotoPerfil
) {}
