package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.comentario.CriarComentarioDto;
import com.maace.connectEtec.models.ComentarioModel;
import com.maace.connectEtec.models.PostModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.ComentarioRepository;
import com.maace.connectEtec.repositories.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    PostRepository postRepository;

    public Boolean criar(UsuarioModel usuario, UUID idPost, CriarComentarioDto comentarioDto) {
        Optional<PostModel> post = postRepository.findById(idPost);

        ComentarioModel comentario = new ComentarioModel();

        if ((comentarioDto.conteudo() != null ||
                comentarioDto.urlMidia() != null) &&
                post.isPresent())
        {
            BeanUtils.copyProperties(comentarioDto, comentario);
            comentario.setLoginAutor(usuario.getLogin());

            comentarioRepository.save(comentario);

            post.get().adicionarComentario(comentario.getIdComentario());

            postRepository.save(post.get());

            return true;
        }
        return false;
    }
}
