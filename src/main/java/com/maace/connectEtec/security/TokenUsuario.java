package com.maace.connectEtec.security;

import com.maace.connectEtec.models.UsuarioModel;

import java.util.Random;

public class TokenUsuario {
    public static Integer gerarToken(){
        return new Random().nextInt(90000) + 10000;
    }

    public static boolean verificarToken(UsuarioModel usuario, Integer tokenRecebido){
        return usuario.getToken().equals(tokenRecebido);
    }
}
