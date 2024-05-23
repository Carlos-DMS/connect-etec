package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.perfilGrupo.AcessarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.GrupoRepository;
import com.maace.connectEtec.repositories.PerfilGrupoRepository;
import com.maace.connectEtec.repositories.PostRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PerfilGrupoService {

    @Autowired
    PerfilGrupoRepository repository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    UsuarioService usuarioService;

    public Optional<PerfilGrupoModel> buscarPorId(UUID id){
        return repository.findById(id);
    }

    public List<PerfilGrupoModel> listarTodos() {
        return repository.findAll();
    }

    public boolean editarDados(GrupoModel grupo, EditarPerfilGrupoDto perfilGrupoDto, String usuarioToken){
        Optional<PerfilGrupoModel> perfilOptional = repository.findById(grupo.getIdPerfilGrupo());
        List<String> usuariosGrupo = grupo.getLoginUsuarios();
        UsuarioModel usuario = usuarioService.buscarPorToken(usuarioToken);

        if (perfilOptional.isPresent() && usuariosGrupo.contains(usuario.getLogin())) {
            PerfilGrupoModel perfil = perfilOptional.get();

            grupo.setNome(perfilGrupoDto.nome());
            perfilOptional.get().setUrlFotoPerfil(perfilGrupoDto.urlFotoPerfil());
            perfilOptional.get().setSobre(perfilGrupoDto.sobre());

            repository.save(perfil);
            grupoRepository.save(grupo);

            return true;
        }

        return false;
    }

    public AcessarPerfilGrupoDto acessarPerfilGrupo(UUID id){
        Optional<GrupoModel> grupo = grupoRepository.findById(id);
        Optional<PerfilGrupoModel> perfil = buscarPerfilGrupo(id);

        if(grupo.isPresent()){
            return new AcessarPerfilGrupoDto(
                    grupo.get().getNome(),
                    perfil.get().getUrlFotoPerfil(),
                    perfil.get().getSobre()
            );
        }

        return null;
    }

    public Optional<PerfilGrupoModel> buscarPerfilGrupo(UUID idGrupo){
        Optional<GrupoModel> grupo = grupoRepository.findById(idGrupo);
        UUID idPerfilGrupo = grupo.get().getIdGrupo();

        return repository.findById(idPerfilGrupo);
    }

    public List<Optional<RespostaPostDto>> buscarPosts(UUID id){
        Optional<PerfilGrupoModel> perfilGrupo = buscarPerfilGrupo(id);
        Optional<GrupoModel> grupo = grupoRepository.findById(id);

        List<UUID> idPosts = perfilGrupo.get().getIdPosts();
        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        if(grupo.isEmpty()) return null;

        for(UUID idPost : idPosts){
            Optional<PostModel> post = postRepository.findById(idPost);
            Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioService.buscarPerfil(post.get().getLoginAutor());
            UsuarioModel usuario = usuarioRepository.findByLogin(post.get().getLoginAutor());

            posts.add(Optional.of(new RespostaPostDto(
                    post.get().getIdPost(),
                    perfilUsuarioService.selecionarNomeExibido(usuario),
                    perfilUsuario.get().getUrlFotoPerfil(),
                    grupo.get().getNome(),
                    perfilGrupo.get().getUrlFotoPerfil(),
                    post.get().getUrlMidia(),
                    post.get().getMomentoPublicacao(),
                    post.get().getConteudo(),
                    post.get().getQtdLike(),
                    post.get().getTagRelatorio()
            )));
        }

        return null;
    }
}
