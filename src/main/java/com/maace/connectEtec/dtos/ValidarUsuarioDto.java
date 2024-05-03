package com.maace.connectEtec.dtos;

import com.maace.connectEtec.models.EnumTipoUsuario;

public record ValidarUsuarioDto (String nomeCompleto, String nomeSocial, EnumTipoUsuario tipoUsuario){
}
