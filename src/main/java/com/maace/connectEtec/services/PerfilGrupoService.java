package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.grupo.IdGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.AcessarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarDadosPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarFotoPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PerfilGrupoService {

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    UsuarioService usuarioService;

    public RespostaPerfilGrupoDto buscarPorId(IdGrupoDto idGrupo) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if (grupo.isPresent()) {
            Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());
            return new RespostaPerfilGrupoDto(grupo.get().getIdGrupo().toString(), grupo.get().getNome(), perfilOptional.get().getUrlFotoPerfil());
        }
        return null;
    }

    public List<RespostaPerfilGrupoDto> listarTodos() {
        List<GrupoModel> grupos = grupoRepository.findAll();

        if (!grupos.isEmpty()) {
            List<RespostaPerfilGrupoDto> perfisDto = new ArrayList<>();
            for (GrupoModel grupo : grupos) {
                Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.getIdPerfilGrupo());
                perfisDto.add(new RespostaPerfilGrupoDto(grupo.getIdGrupo().toString(), grupo.getNome(), perfilOptional.get().getUrlFotoPerfil()));
            }
            return perfisDto;
        }
        return null;
    }

    public boolean editarDados(EditarDadosPerfilGrupoDto perfilGrupoDto, UsuarioModel usuario) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(perfilGrupoDto.idGrupo()));

        if (grupo.isPresent()) {
            Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());

            if (grupo.get().getLoginModeradores().contains(usuario.getLogin()) || grupo.get().getLoginDono().equals(usuario.getLogin())) {
                PerfilGrupoModel perfil = perfilOptional.get();

                grupo.get().setNome(perfilGrupoDto.nome());
                perfilOptional.get().setSobre(perfilGrupoDto.sobre());

                perfilGrupoRepository.save(perfil);
                grupoRepository.save(grupo.get());

                return true;
            }
        }
        return false;
    }

    public boolean editarFotoPerfil(EditarFotoPerfilGrupoDto fotoDto, String usuarioToken) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(fotoDto.idGrupo()));

        if (grupo.isPresent()) {
            Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());
            UsuarioModel usuario = usuarioService.buscarPorToken(usuarioToken);

            if (grupo.get().getLoginModeradores().contains(usuario.getLogin()) || grupo.get().getLoginDono().equals(usuario.getLogin())) {

                PerfilGrupoModel perfil = perfilOptional.get();
                perfil.setUrlFotoPerfil(fotoDto.urlFotoPerfil());

                perfilGrupoRepository.save(perfil);
                grupoRepository.save(grupo.get());

                return true;
            }
        }
        return false;
    }

    public List<Optional<RespostaPostDto>> buscarPosts(IdGrupoDto idGrupo, String login) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if (grupo.isPresent()) {
            PerfilGrupoModel perfil = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo()).get();
            List<UUID> idPosts = perfil.getIdPosts();
            List<Optional<RespostaPostDto>> posts = new ArrayList<>();

            for (UUID idPost : idPosts) {
                Optional<PostModel> post = postRepository.findById(idPost);

                if (post.isPresent()) {
                    Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioService.buscarPerfil(post.get().getLoginAutor());
                    UsuarioModel usuario = usuarioRepository.findByLogin(post.get().getLoginAutor());

                    posts.add(Optional.of(new RespostaPostDto(
                            post.get().getIdPost(),
                            perfilUsuarioService.selecionarNomeExibido(usuario),
                            perfilUsuario.get().getUrlFotoPerfil(),
                            grupo.get().getNome(),
                            perfil.getUrlFotoPerfil(),
                            post.get().getUrlMidia(),
                            post.get().getConteudo(),
                            post.get().momentoFormatado(),
                            usuario.getLogin(),
                            post.get().getQtdLike(),
                            post.get().getQtdComentarios(),
                            postCurtidoPeloUsuario(login, post.get().getIdPost()),
                            post.get().getTagRelatorio()
                    )));
                }
            }
            return posts;
        }
        return null;
    }

    public AcessarPerfilGrupoDto acessarPerfilGrupo(IdGrupoDto idGrupo) {
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(idGrupo.idGrupo()));

        if (grupo.isPresent()) {
            Optional<PerfilGrupoModel> perfilOptional = perfilGrupoRepository.findById(grupo.get().getIdPerfilGrupo());
            return new AcessarPerfilGrupoDto(
                    grupo.get().getNome(),
                    perfilOptional.get().getUrlFotoPerfil(),
                    perfilOptional.get().getSobre()
            );
        }
        return null;
    }

    public Boolean postCurtidoPeloUsuario(String login, UUID idPost) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        if (perfil.isPresent()) {
            return perfil.get().getIdPostsCurtidos().contains(idPost);
        }
        return null;
    }
}
