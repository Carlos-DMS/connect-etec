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
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    public boolean criarGrupo(CriarGrupoDto grupoDto, String loginDono) {
        List<GrupoModel> grupos = grupoRepository.findAll();
        for(GrupoModel grupo : grupos){
            if(Objects.equals(grupo.getNome(), grupoDto.nome())){
                return false;
            }
        }

        PerfilGrupoModel perfilGrupo = new PerfilGrupoModel();
        perfilGrupoRepository.save(perfilGrupo);

        GrupoModel grupo = new GrupoModel();
        grupo.setNome(grupoDto.nome());
        grupo.setLoginDono(loginDono);
        grupo.setIdPerfilGrupo(perfilGrupo.getIdPerfil());
        grupoRepository.save(grupo);
        return true;
    }

    public RespostaGrupoDto buscarPorId(IdGrupoDto idGrupo) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if(grupo.isPresent()){
            Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());
            return new RespostaGrupoDto(grupo.get().getIdGrupo(), grupo.get().getNome(), perfilOptional.get().getUrlFotoPerfil());
        }

        return null;
    }

    public boolean deletarGrupo(String loginUsuario, IdGrupoDto idGrupo) {
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if (grupo.isPresent()){
            if (usuario.getTipoUsuario() == EnumTipoUsuario.ADMINISTRADOR
                    || Objects.equals(loginUsuario, grupo.get().getLoginDono()))
            {
                perfilGrupoRepository.deleteById(grupo.get().getIdPerfilGrupo());
                grupoRepository.deleteById(UUID.fromString(idGrupo.idGrupo()));

                return true;
            }
        }

        return false;
    }

    public List<UserDetails> todosMembros(IdGrupoDto idGrupo) { //retorna todos sem exceção
        Optional<GrupoModel> grupoOptional = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if (grupoOptional.isPresent()) {
            GrupoModel grupo = grupoOptional.get();
            List<String> logins = new ArrayList<>();
            logins.addAll(grupo.getLoginUsuarios());
            logins.addAll(grupo.getLoginModeradores());
            logins.add(grupo.getLoginDono());
            List<UserDetails> membros = new ArrayList<>();

            for(String login : logins) {
                membros.add(usuarioService.loadUserByUsername(login));
            }

            return membros;
        }

        return null;
    }

    public boolean tornarModerador(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if(grupo.isPresent() && !grupo.get().getLoginModeradores().contains(loginUsuario) && !Objects.equals(grupo.get().getLoginDono(), loginUsuario)){
            grupo.get().getLoginUsuarios().remove(loginUsuario);
            grupo.get().getLoginModeradores().add(loginUsuario);
            grupoRepository.save(grupo.get());
            return true;
        }

        return false;
    }

    public boolean rebaixarModerador(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if(grupo.isPresent() && grupo.get().getLoginModeradores().contains(loginUsuario) && !Objects.equals(grupo.get().getLoginDono(), loginUsuario)){
            grupo.get().getLoginModeradores().remove(loginUsuario);
            grupo.get().getLoginUsuarios().add(loginUsuario);
            grupoRepository.save(grupo.get());
            return true;
        }

        return false;
    }

    public boolean entrarNoGrupo(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if(grupo.isPresent()
                && !grupo.get().getLoginUsuarios().contains(loginUsuario)
                && !grupo.get().getLoginModeradores().contains(loginUsuario)
                && !Objects.equals(grupo.get().getLoginDono(), loginUsuario)){
            grupo.get().getLoginUsuarios().add(loginUsuario);
            grupoRepository.save(grupo.get());
            return true;
        }

        return false;
    }

    public boolean removerUsuario(String loginUsuario, IdGrupoDto idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if(grupo.isPresent()){
            if(grupo.get().getLoginUsuarios().contains(loginUsuario)){
                grupo.get().getLoginUsuarios().remove(loginUsuario);
                grupoRepository.save(grupo.get());
            }
            if(grupo.get().getLoginModeradores().contains(loginUsuario)){
                grupo.get().getLoginModeradores().remove(loginUsuario);
                grupoRepository.save(grupo.get());
            }
            return true;
        }

        return false;
    }
}
