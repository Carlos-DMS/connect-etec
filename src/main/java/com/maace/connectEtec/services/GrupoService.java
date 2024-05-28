package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.grupo.CriarGrupoDto;
import com.maace.connectEtec.dtos.grupo.IdGrupoDto;
import com.maace.connectEtec.dtos.grupo.RespostaGrupoDto;
import com.maace.connectEtec.models.EnumTipoUsuario;
import com.maace.connectEtec.models.GrupoModel;
import com.maace.connectEtec.models.PerfilGrupoModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.GrupoRepository;
import com.maace.connectEtec.repositories.PerfilGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    public void criarGrupo(CriarGrupoDto grupoDto, String donoLogin) {
        PerfilGrupoModel perfilGrupo = new PerfilGrupoModel();
        perfilGrupo.setUrlFotoPerfil(grupoDto.urlFotoPerfil());
        perfilGrupo.setSobre(grupoDto.sobre());
        perfilGrupoRepository.save(perfilGrupo);

        GrupoModel grupo = new GrupoModel();
        grupo.setNome(grupoDto.nome());
        grupo.setLoginDono(donoLogin);
        grupo.setIdPerfilGrupo(perfilGrupo.getIdPerfil());
        grupoRepository.save(grupo);
    }

    public RespostaGrupoDto buscarPorId(UUID id) {
        Optional<GrupoModel> grupo = grupoRepository.findById(id);

        if(grupo.isPresent()){
            Optional<PerfilGrupoModel> perfil = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());
            return new RespostaGrupoDto(grupo.get().getIdGrupo(), grupo.get().getNome(), perfil.get().getUrlFotoPerfil());
        }
        return null;
    }

    public List<RespostaGrupoDto> listarTodos() {
        List<GrupoModel> grupos = grupoRepository.findAll();
        if(!grupos.isEmpty()){
            List<RespostaGrupoDto> gruposDto = new ArrayList<>();
            for(GrupoModel grupo : grupos){
                Optional<PerfilGrupoModel> perfil = perfilGrupoRepository.findById(grupo.getIdPerfilGrupo());
                gruposDto.add(new RespostaGrupoDto(grupo.getIdGrupo(), grupo.getNome(), perfil.get().getUrlFotoPerfil()));
            }
            return gruposDto;
        }
        return null;
    }

    public boolean tornarModerador(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.id()));

        if(grupo.isPresent() && !grupo.get().getLoginModeradores().contains(loginUsuario)){
            grupo.get().getLoginModeradores().add(loginUsuario);
            return true;
        }

        return false;
    }

    public boolean rebaixarModerador(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.id()));

        if(grupo.isPresent() && grupo.get().getLoginModeradores().contains(loginUsuario)){
            grupo.get().getLoginModeradores().remove(loginUsuario);

            return true;
        }

        return false;
    }

    public boolean entrarNoGrupo(String loginUsuario, UUID id){
        Optional<GrupoModel> grupo = grupoRepository.findById(id);
        if(grupo.isPresent() && !grupo.get().getLoginUsuarios().contains(loginUsuario)){
            grupo.get().getLoginUsuarios().add(loginUsuario);
            grupoRepository.save(grupo.get());
            return true;
        }
        return false;
    }

    public boolean removerUsuario(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.id()));
        if(grupo.isPresent()){
            if(grupo.get().getLoginUsuarios().contains(loginUsuario)){
                grupo.get().getLoginUsuarios().remove(loginUsuario);
            }
            if(grupo.get().getLoginModeradores().contains(loginUsuario)){
                grupo.get().getLoginModeradores().remove(loginUsuario);
            }
            return true;
        }
        return false;
    }

    public boolean deletarGrupo(UUID id, String userToken) {
        UsuarioModel usuario = usuarioService.buscarPorToken(userToken);
        Optional<GrupoModel> grupo = grupoRepository.findById(id);

        if (usuario != null && grupo.isPresent()){
            if (usuario.getTipoUsuario() == EnumTipoUsuario.ADMINISTRADOR
                    || Objects.equals(usuario.getLogin(), grupo.get().getLoginDono()))
            {
                perfilGrupoRepository.deleteById(grupo.get().getIdPerfilGrupo());
                grupoRepository.deleteById(id);

                return true;
            }
        }
        return false;
    }

    public List<UserDetails> todosMembros(UUID id) { //retorna todos sem exceção
        Optional<GrupoModel> grupoOptional = grupoRepository.findById(id);

        if (grupoOptional.isPresent()) {
            GrupoModel grupo = grupoOptional.get();
            List<String> logins = grupo.getLoginUsuarios();
            List<UserDetails> membros = new ArrayList<>();

            for(String login : logins) {
                membros.add(usuarioService.loadUserByUsername(login));
            }

            return membros;
        }
        return null;
    }
}
