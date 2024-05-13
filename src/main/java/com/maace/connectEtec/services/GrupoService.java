package com.maace.connectEtec.services;

import com.maace.connectEtec.models.EnumTipoUsuario;
import com.maace.connectEtec.models.GrupoModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository repository;

    @Autowired
    UsuarioService usuarioService;

    public void criarGrupo(GrupoModel grupo) {
        repository.save(grupo);
    }

    public Optional<GrupoModel> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public List<GrupoModel> listarTodos() {
        return repository.findAll();
    }

    public boolean atualizarGrupo(UUID id, String nome) {
        Optional<GrupoModel> antigoGrupo = buscarPorId(id);

        if (antigoGrupo.isPresent()) {
            GrupoModel novoGrupo = antigoGrupo.get();
            novoGrupo.setNome(nome);
            repository.save(novoGrupo);
            return true;
        }

        return false;
    }

    public boolean deletarGrupo(UUID id, String userToken) {
        UsuarioModel usuario = usuarioService.buscarPorToken(userToken);
        Optional<GrupoModel> grupo = buscarPorId(id);

        if (usuario == null || grupo.isEmpty()) return false;

        if (EnumTipoUsuario.ADMINISTRADOR == usuario.getTipoUsuario()
                || Objects.equals(grupo.get().getLoginDono(), usuario.getLogin())) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<UserDetails> todosUsuarios(UUID id) { //retorna somente usuarios comuns
        Optional<GrupoModel> grupo = buscarPorId(id);
        List<String> logins = grupo.map(GrupoModel::getLoginUsuarios).orElse(null);
        List<UserDetails> usuarios = new ArrayList<>();

        for(String login : logins) usuarios.add(usuarioService.loadUserByUsername(login));

        return usuarios;
    }

    public List<UserDetails> todosAdmins(UUID id) { //retorna somente administradores
        Optional<GrupoModel> grupo = buscarPorId(id);
        List<String> logins = grupo.map(GrupoModel::getLoginAdmins).orElse(null);
        List<UserDetails> admins = new ArrayList<>();

        for(String login : logins) admins.add(usuarioService.loadUserByUsername(login));

        return admins;
    }

    public List<UserDetails> todosMembros(UUID id) { //retorna todos sem exceção
        Optional<GrupoModel> grupoOptional = buscarPorId(id);

        if (grupoOptional.isPresent()) {
            GrupoModel grupo = grupoOptional.get();
            List<String> logins = grupo.getLoginUsuarios();
            List<UserDetails> membros = new ArrayList<>();

            for(String login : logins) membros.add(usuarioService.loadUserByUsername(login));

            return membros;
        }

        return null;
    }
}
