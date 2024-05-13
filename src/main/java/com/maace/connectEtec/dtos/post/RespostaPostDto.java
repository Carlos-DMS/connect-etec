package com.maace.connectEtec.dtos.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RespostaPostDto(
        UUID idPost,
        String nomeAutor,
        String urlFotoDePerfilUsuario,
        String nomeGrupo,
        String urlFotoDePerfilGrupo,
        String urlMidia,
        LocalDateTime momentoPublicacao,
        String conteudo,
        Integer qtdLike,
        List<String> tags
) { }
