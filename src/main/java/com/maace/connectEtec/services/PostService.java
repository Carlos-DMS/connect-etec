package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.post.CriarPostDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
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
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

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

    public Boolean curtir(UUID idPost, boolean estaCurtido) {
        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent()) {
            if (estaCurtido) {
                post.get().darLike();
                return true;
            }
            else {
                post.get().removerLike();
                return false;
            }
        }
        return null;
    }
}
