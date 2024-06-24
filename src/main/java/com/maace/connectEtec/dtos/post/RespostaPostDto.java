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
        String loginAutor,
        Integer qtdLike,
        Integer qtdComentarios,
        Boolean postCurtido,
        String tag,
        Boolean usuarioADM,
        Boolean postDenunciado,
        Integer blockDenuncia
) { }
