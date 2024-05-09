package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.CriarPostDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.PostModel;
import com.maace.connectEtec.models.UsuarioModel;
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
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;


    public void criar (CriarPostDto criarPostDto, UsuarioModel usuario) {
        PostModel post = new PostModel();

        post.setLoginAutor(usuario.getLogin());
        post.setConteudo(criarPostDto.conteudo());
        post.setUrlMidia(criarPostDto.urlMidia());

        if (criarPostDto.idGrupo() == null) {
            post.setIdGrupo(null);
        }
        else{
            post.setIdGrupo(UUID.fromString(criarPostDto.idGrupo()));
        }

        for (String tag : criarPostDto.tags()) {
            post.addTag(tag);
        }

        postRepository.save(post);

        UUID id = post.getIdPost();

        Optional<PerfilUsuarioModel> perfil = perfilUsuarioService.buscarPerfil(usuario.getLogin());

        perfil.get().addIdPost(id);

        perfilUsuarioRepository.save(perfil.get());
    }
}
