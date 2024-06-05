package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.comentario.CriarComentarioDto;
import com.maace.connectEtec.dtos.comentario.RespostaComentarioDto;
import com.maace.connectEtec.models.ComentarioModel;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.PostModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.ComentarioRepository;
import com.maace.connectEtec.repositories.PerfilUsuarioRepository;
import com.maace.connectEtec.repositories.PostRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    public Boolean criar(UsuarioModel usuario, UUID idPost, CriarComentarioDto comentarioDto) {
        Optional<PostModel> post = postRepository.findById(idPost);

        ComentarioModel comentario = new ComentarioModel();

        if ((comentarioDto.conteudo() != null ||
                comentarioDto.urlMidia() != null) &&
                post.isPresent()) {
            BeanUtils.copyProperties(comentarioDto, comentario);
            comentario.setLoginAutor(usuario.getLogin());

            comentarioRepository.save(comentario);

            post.get().adicionarComentario(comentario.getIdComentario());

            postRepository.save(post.get());

            return true;
        }
        return false;
    }

    public List<RespostaComentarioDto> listarComentarios(UsuarioModel usuario, UUID idPost) {
        List<RespostaComentarioDto> comentarios = new ArrayList<>();

        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent()) {
            for (UUID idComentario : post.get().getIdComentarios()) {
                Optional<ComentarioModel> comentario = comentarioRepository.findById(idComentario);

                if (comentario.isPresent()) {
                    UsuarioModel autor = usuarioRepository.findByLogin(comentario.get().getLoginAutor());
                    Optional<PerfilUsuarioModel> perfilAutor = perfilUsuarioRepository.findById(autor.getIdPerfilUsuario());

                    if (perfilAutor.isPresent()) {
                        comentarios.add(new RespostaComentarioDto(
                                comentario.get().getIdComentario(),
                                perfilUsuarioService.selecionarNomeExibido(autor),
                                perfilAutor.get().getUrlFotoPerfil(),
                                comentario.get().getUrlMidia(),
                                comentario.get().getConteudo(),
                                comentario.get().momentoFormatado(),
                                comentario.get().getQtdLike(),
                                comentarioCurtidoPeloUsuario(
                                        usuario.getLogin(),
                                        comentario.get().getIdComentario()
                                )
                        ));
                    }
                }
            }
            Collections.reverse(comentarios);

            return comentarios;
        }
        return null;
    }

    public Boolean curtir(String login, UUID idComentario, boolean estaCurtido) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        Optional<ComentarioModel> comentario = comentarioRepository.findById(idComentario);

        if (comentario.isPresent() && perfil.isPresent()) {
            if (!estaCurtido) {
                comentario.get().curtir();
                perfil.get().curtirComentario(idComentario);

                comentarioRepository.save(comentario.get());
                perfilUsuarioRepository.save(perfil.get());

                return true;
            }
            else {
                comentario.get().removerCurtida();
                perfil.get().removerCurtidaComentario(idComentario);

                comentarioRepository.save(comentario.get());
                perfilUsuarioRepository.save(perfil.get());

                return false;
            }
        }
        return null;
    }

    public Boolean comentarioCurtidoPeloUsuario(String login, UUID idComentario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        if (perfil.isPresent()) {
            return perfil.get().getIdComentariosCurtidos().contains(idComentario);
        }
        return null;
    }
}
