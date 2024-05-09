package com.maace.connectEtec.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record RespostaPostDto(
        UUID idPost,
        String nomeAutor,
        String urlFotoPerfilUsuario,
        String nomeGrupo,
        String urlFotoPerfilGrupo,
        String urlMidia,
        LocalDateTime momentoPublicacao,
        String conteudo,
        Integer qtdLike,
        String tag
) { }
