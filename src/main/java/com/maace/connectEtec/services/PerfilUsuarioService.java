package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.PerfilUsuarioRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PerfilUsuarioService {

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void salvarPerfilUsuario(PerfilUsuarioModel perfilUsuario){
        perfilUsuarioRepository.save(perfilUsuario);
    }

    public List<Optional<RespostaPerfilUsuarioDto>> listaPerfis(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Optional<RespostaPerfilUsuarioDto>> perfis = new ArrayList<>();

        String nome;

        for (UsuarioModel usuario : usuarios) {
            UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(idPerfilUsuario);

            if (usuario.getNomeSocial() != null) {
                nome = usuario.getNomeSocial();
            }
            else{
                nome = usuario.getNomeCompleto();
            }

            perfis.add(Optional.of(new RespostaPerfilUsuarioDto(nome, perfil.get().getUrlFotoPerfil(), perfil.get().getSobre())));
        }
        return perfis;
    }

    public Optional<PerfilUsuarioModel> buscarPerfil(String loginUsuario){
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);

        UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

        return perfilUsuarioRepository.findById(idPerfilUsuario);
    }

    //REALMENTE NECESSARIO? FAZER UM UPDATE? SIM!
}
