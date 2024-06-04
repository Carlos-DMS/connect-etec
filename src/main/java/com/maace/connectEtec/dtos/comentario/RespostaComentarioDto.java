package com.maace.connectEtec.dtos.comentario;

import java.util.UUID;

public record RespostaComentarioDto(
        UUID idComentario,
        String nomeAutor,
        String urlFotoPerfilUsuario,
        String urlMidia,
        String conteudo,
        String momento,
        Integer qtdLike,
        Boolean comentarioCurtido
) { }
