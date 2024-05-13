package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.perfilGrupo.AtualizarPerfilGrupoDto;
import com.maace.connectEtec.models.PerfilGrupoModel;
import com.maace.connectEtec.repositories.PerfilGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PerfilGrupoService {

    @Autowired
    PerfilGrupoRepository repository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GrupoService grupoService;

    public PerfilGrupoModel criarPerfilGrupo(PerfilGrupoModel perfilGrupo) {
        return repository.save(perfilGrupo);
    } //nao precisa de um criar, so update

    public Optional<PerfilGrupoModel> buscarPorId(UUID id){
        return repository.findById(id);
    }

    public List<PerfilGrupoModel> listarTodos() {
        return repository.findAll();
    }

    public PerfilGrupoModel atualizarPerfilGrupo(UUID id, AtualizarPerfilGrupoDto perfilGrupoDto){
        Optional<PerfilGrupoModel> perfilOptional = repository.findById(id);

        if (perfilOptional.isPresent()) {
            PerfilGrupoModel perfil = perfilOptional.get();

            if (perfilGrupoDto.sobre() != null) perfil.setSobre(perfilGrupoDto.sobre());
            if (perfilGrupoDto.urlFotoPerfil() != null) perfil.setUrlFotoPerfil(perfilGrupoDto.urlFotoPerfil());

            return repository.save(perfil);
        }

        return null;
    }

    public void deletarPerfilGrupo(UUID id, String userToken){
        if(grupoService.deletarGrupo(id, userToken)) repository.deleteById(id);
    }
}
