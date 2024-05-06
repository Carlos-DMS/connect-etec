package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.repositories.PerfilUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PerfilUsuarioService {

    @Autowired
    PerfilUsuarioRepository repository;

    public void salvarPerfilUsuario(PerfilUsuarioModel perfilUsuario){
        repository.save(perfilUsuario);
    }

    public List<RespostaPerfilUsuarioDto> listarTodos(){
        List<PerfilUsuarioModel> perfilUsuarios = repository.findAll();
        List<RespostaPerfilUsuarioDto> perfilUsuariosDto = new ArrayList<>();

        for (PerfilUsuarioModel perfilUsuario : perfilUsuarios){
            RespostaPerfilUsuarioDto respostaPerfilUsuarioDto = new RespostaPerfilUsuarioDto(
                    perfilUsuario.getIdPerfil(),
                    perfilUsuario.getUrlFotoPerfil(),
                    perfilUsuario.getUrlBanner(),
                    perfilUsuario.getSobre(),
                    perfilUsuario.getGrupos()
            );
            perfilUsuariosDto.add(respostaPerfilUsuarioDto);
        }
        return perfilUsuariosDto;
    }

    public PerfilUsuarioModel buscarPorId(UUID id){
        return repository.findById(id).orElse(null);
    }

    //REALMENTE NECESSARIO? FAZER UM UPDATE?
    /*public void deletar(UUID id) {
        repository.deleteById(id);
    }*/
}
