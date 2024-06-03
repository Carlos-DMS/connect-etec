package com.maace.connectEtec.dtos.post;

import java.util.UUID;

public record RespostaPostDto(
        UUID idPost,
        String nomeAutor,
        String urlFotoPerfilUsuario,
        String nomeGrupo,
        String urlFotoPerfilGrupo,
        String urlMidia,
        String conteudo,
        String momento,
        Integer qtdLike,
        Boolean postCurtido,
        String tag
) { }
