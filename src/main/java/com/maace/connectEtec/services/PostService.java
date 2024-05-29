package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.CriarPostDto;
import com.maace.connectEtec.dtos.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void criar (CriarPostDto criarPostDto, UsuarioModel usuario) {
        PostModel post = new PostModel();

        post.setLoginAutor(usuario.getLogin());
        post.setConteudo(criarPostDto.conteudo());
        post.setUrlMidia(criarPostDto.urlMidia());
        post.setTag(criarPostDto.tag());

        if (criarPostDto.idGrupo() != null) {
            post.setIdGrupo(UUID.fromString(criarPostDto.idGrupo()));
        }
        else{
            post.setIdGrupo(null);
        }

        postRepository.save(post);

        UUID id = post.getIdPost();

        Optional<PerfilUsuarioModel> perfil = perfilUsuarioService.buscarPerfil(usuario.getLogin());

        perfil.get().addIdPost(id);

        perfilUsuarioRepository.save(perfil.get());
    }

    public List<Optional<RespostaPostDto>> listarPosts() {

        List<PostModel> posts = postRepository.findAll();
        List<Optional<RespostaPostDto>> postsDto = new ArrayList<>();

        for (PostModel post : posts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            UsuarioModel usuario = usuarioRepository.findByLogin(post.getLoginAutor());
            Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

            if (post.getIdGrupo() != null) {
                grupo = grupoRepository.findById(post.getIdGrupo());
                perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
            }

            postsDto.add(Optional.of(new RespostaPostDto(
                    post.getIdPost(),
                    perfilUsuarioService.selecionarNomeExibido(usuario),
                    perfilUsuario.get().getUrlFotoPerfil(),
                    (grupo.isPresent()) ? grupo.get().getNome() : null,
                    (perfilGrupo.isPresent()) ? perfilGrupo.get().getUrlFotoPerfil() : null,
                    post.getUrlMidia(),
                    post.getMomentoPublicacao(),
                    post.getConteudo(),
                    post.getQtdLike(),
                    post.getTagRelatorio()
            )));
        }
        Collections.reverse(postsDto);

        return postsDto;
    }
}
