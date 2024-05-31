package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.post.CriarPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.GrupoRepository;
import com.maace.connectEtec.repositories.PerfilGrupoRepository;
import com.maace.connectEtec.repositories.PerfilUsuarioRepository;
import com.maace.connectEtec.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    public void criar(CriarPostDto criarPostDto, UsuarioModel usuario) {
        PostModel post = new PostModel();

        post.setLoginAutor(usuario.getLogin());
        post.setConteudo(criarPostDto.conteudo());
        post.setUrlMidia(criarPostDto.urlMidia());
        post.setTag(criarPostDto.tag());

        if(criarPostDto.idGrupo() != null){
            Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(criarPostDto.idGrupo()));
            if(grupo.isPresent()){
                post.setIdGrupo(UUID.fromString(criarPostDto.idGrupo()));
            }
            else{
                post.setIdGrupo(null);
            }
        }
        else{
            post.setIdGrupo(null);
        }

        postRepository.save(post);

        UUID id = post.getIdPost();

        Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioService.buscarPerfil(usuario.getLogin());

        perfilUsuario.get().addIdPost(id);

        if(criarPostDto.idGrupo() != null) {
            Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(criarPostDto.idGrupo()));
            if (grupo.isPresent()) {
                PerfilGrupoModel perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo()).get();
                perfilGrupo.addIdPosts(post.getIdPost());
                perfilGrupoRepository.save(perfilGrupo);
            }
        }

        perfilUsuarioRepository.save(perfilUsuario.get());
    }
}
