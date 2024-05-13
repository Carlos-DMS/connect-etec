package com.maace.connectEtec.dtos.perfilUsuario;

import java.util.List;
import java.util.UUID;

public record PerfilUsuarioDto(
        String urlFotoPerfil,
        String sobre,
        List<UUID>idPosts,
        List<String> loginConexoes,
        List<UUID> idGrupos
) { }
