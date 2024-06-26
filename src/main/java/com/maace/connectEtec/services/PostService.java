package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.post.CriarPostDto;
import com.maace.connectEtec.dtos.post.RespostaDenunciasPostDto;
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
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Boolean criar(CriarPostDto criarPostDto, UsuarioModel usuario) {
        PostModel post = new PostModel();

        if (criarPostDto.conteudo() != null || criarPostDto.urlMidia() != null) {
            post.setLoginAutor(usuario.getLogin());
            post.setConteudo(criarPostDto.conteudo());
            post.setUrlMidia(criarPostDto.urlMidia());
            post.setTag(criarPostDto.tag());

            if (criarPostDto.idGrupo() != null) {
                post.setIdGrupo(UUID.fromString(criarPostDto.idGrupo()));
            } else {
                post.setIdGrupo(null);
            }

            postRepository.save(post);

            UUID id = post.getIdPost();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioService.buscarPerfil(usuario.getLogin());

            perfil.get().addIdPost(id);

            perfilUsuarioRepository.save(perfil.get());

            return true;
        }
        return false;
    }

    public List<Optional<RespostaPostDto>> listarPosts(UsuarioModel usuario) {

        List<PostModel> posts = postRepository.findAll();
        List<Optional<RespostaPostDto>> postsDto = new ArrayList<>();

        posts.sort(Comparator.comparing(PostModel::getMomentoPublicacao).reversed());

        for (PostModel post : posts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            UsuarioModel usuarioAutor = usuarioRepository.findByLogin(post.getLoginAutor());
            Optional<PerfilUsuarioModel> perfilUsuarioAutor = perfilUsuarioRepository.findById(usuarioAutor.getIdPerfilUsuario());

            if (post.getIdGrupo() != null) {
                grupo = grupoRepository.findById(post.getIdGrupo());
                if (grupo.isPresent()) {
                    perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                }
            }

            if (post.getQtdDenuncia() < 5 && post.getBlockDenuncia() != 1) {
                postsDto.add(Optional.of(new RespostaPostDto(
                        post.getIdPost(),
                        perfilUsuarioService.selecionarNomeExibido(usuarioAutor),
                        perfilUsuarioAutor.get().getUrlFotoPerfil(),
                        grupo.isPresent() ? grupo.get().getNome() : null,
                        perfilGrupo.isPresent() ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.getUrlMidia(),
                        post.getConteudo(),
                        post.momentoFormatado(),
                        post.getLoginAutor(),
                        post.getQtdLike(),
                        post.getQtdComentarios(),
                        postCurtidoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getTagRelatorio(),
                        usuarioAutor.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR),
                        postDenunciadoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getBlockDenuncia()
                )));
            }
        }
        return postsDto;
    }

    public List<Optional<RespostaDenunciasPostDto>> listarPostsComDenuncia(UsuarioModel usuario) {

        List<PostModel> posts = postRepository.findAll();
        List<Optional<RespostaDenunciasPostDto>> postsDto = new ArrayList<>();

        posts.sort(Comparator.comparing(PostModel::getMomentoPublicacao).reversed());

        for (PostModel post : posts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            UsuarioModel usuarioAutor = usuarioRepository.findByLogin(post.getLoginAutor());
            Optional<PerfilUsuarioModel> perfilUsuarioAutor = perfilUsuarioRepository.findById(usuarioAutor.getIdPerfilUsuario());

            if (post.getIdGrupo() != null) {
                grupo = grupoRepository.findById(post.getIdGrupo());
                if (grupo.isPresent()) {
                    perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                }
            }

            if (post.getQtdDenuncia() >= 5 && post.getBlockDenuncia() == 0) {
                postsDto.add(Optional.of(new RespostaDenunciasPostDto(
                        post.getIdPost(),
                        perfilUsuarioService.selecionarNomeExibido(usuarioAutor),
                        perfilUsuarioAutor.get().getUrlFotoPerfil(),
                        grupo.isPresent() ? grupo.get().getNome() : null,
                        perfilGrupo.isPresent() ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.getUrlMidia(),
                        post.getConteudo(),
                        post.momentoFormatado(),
                        post.getLoginAutor(),
                        post.getQtdLike(),
                        post.getQtdComentarios(),
                        postCurtidoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getTagRelatorio(),
                        usuarioAutor.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR),
                        postDenunciadoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getQtdDenuncia()
                )));
            }
        }
        return postsDto;
    }

    public Boolean curtir(String login, UUID idPost, boolean estaCurtido) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent() && perfil.isPresent()) {
            if (!estaCurtido) {
                post.get().curtir();
                perfil.get().curtirPost(idPost);

                postRepository.save(post.get());
                perfilUsuarioRepository.save(perfil.get());

                return true;
            } else {
                post.get().removerCurtida();
                perfil.get().removerCurtidaPost(idPost);

                postRepository.save(post.get());
                perfilUsuarioRepository.save(perfil.get());

                return false;
            }
        }
        return null;
    }

    public Boolean denunciar(String login, UUID idPost, boolean estaDenunciado) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent() && perfil.isPresent()) {
            if (!estaDenunciado) {
                post.get().addDenuncia();
                perfil.get().denunciarPost(idPost);

                postRepository.save(post.get());
                perfilUsuarioRepository.save(perfil.get());

                return true;
            } else {
                post.get().removerDenuncia();
                perfil.get().removerDenunciaPost(idPost);

                postRepository.save(post.get());
                perfilUsuarioRepository.save(perfil.get());

                return false;
            }
        }
        return null;
    }

    public Boolean gerenciarBlock(UUID idPost, Integer block) {
        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent()) {
            if (block == 1) {
                post.get().bloquearDenuncia();

                postRepository.save(post.get());
                return true;
            } else if (block == 2) {
                post.get().desbloquearDenuncia();

                postRepository.save(post.get());
                return true;
            } else {
                return false;
            }
        }
        return null;
    }


    public Boolean deletarPost(UsuarioModel usuario, UUID idPost) {
        Optional<PostModel> post = postRepository.findById(idPost);

        if (post.isPresent()) {
            UUID idGrupo = post.get().getIdGrupo();

            if (usuario.getLogin().equals(post.get().getLoginAutor()) ||
                    usuario.getTipoUsuario() == EnumTipoUsuario.ADMINISTRADOR) {
                postRepository.delete(post.get());
                return true;
            }

            if (idGrupo != null) {
                Optional<GrupoModel> grupo = grupoRepository.findById(idGrupo);

                if (grupo.isPresent()) {
                    if (usuario.getLogin().equals(grupo.get().getLoginDono()) ||
                            grupo.get().getLoginModeradores().contains(usuario.getLogin())) {
                        postRepository.delete(post.get());
                        return true;
                    }
                }
            }
            return false;
        }
        return null;
    }

    public List<RespostaPostDto> postsUsuariosSeguidos(UsuarioModel usuario) {
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        List<PostModel> postsModel = new ArrayList<>();
        List<RespostaPostDto> postsDTO = new ArrayList<>();

        if (perfil.isPresent()) {
            for (String loginUsuarioSeguido : perfil.get().getLoginUsuariosSeguidos()) {
                UsuarioModel usuarioSeguido = usuarioRepository.findByLogin(loginUsuarioSeguido);
                Optional<PerfilUsuarioModel> perfilUsuarioSeguido = perfilUsuarioRepository.findById(usuarioSeguido.getIdPerfilUsuario());

                if (perfilUsuarioSeguido.isPresent()) {
                    for (UUID idPost : perfilUsuarioSeguido.get().getIdPosts()) {
                        Optional<PostModel> post = postRepository.findById(idPost);

                        if (post.isPresent()) {
                            postsModel.add(post.get());
                        }
                    }
                }
            }

            postsModel.sort(Comparator.comparing(PostModel::getMomentoPublicacao).reversed());

            for (PostModel post : postsModel) {
                Optional<GrupoModel> grupo = Optional.empty();
                Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

                UsuarioModel usuarioSeguido = usuarioRepository.findByLogin(post.getLoginAutor());
                Optional<PerfilUsuarioModel> perfilUsuarioSeguido = perfilUsuarioRepository.findById(usuarioSeguido.getIdPerfilUsuario());

                if (post.getIdGrupo() != null) {
                    grupo = grupoRepository.findById(post.getIdGrupo());
                    if (grupo.isPresent()) {
                        perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                    }
                }

                postsDTO.add(new RespostaPostDto(
                        post.getIdPost(),
                        perfilUsuarioService.selecionarNomeExibido(usuarioSeguido),
                        perfilUsuarioSeguido.get().getUrlFotoPerfil(),
                        grupo.isPresent() ? grupo.get().getNome() : null,
                        perfilGrupo.isPresent() ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.getUrlMidia(),
                        post.getConteudo(),
                        post.momentoFormatado(),
                        post.getLoginAutor(),
                        post.getQtdLike(),
                        post.getQtdComentarios(),
                        postCurtidoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getTagRelatorio(),
                        usuarioSeguido.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR),
                        postDenunciadoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getBlockDenuncia()
                ));
            }
        }
        return postsDTO;
    }

    public List<RespostaPostDto> buscarPorTag(UsuarioModel usuario, String tag) {
        List<RespostaPostDto> postsDTO = new ArrayList<>();

        List<PostModel> postsModel = postRepository.findByTag(EnumTag.valueOf(tag));

        postsModel.sort(Comparator.comparing(PostModel::getMomentoPublicacao).reversed());

        for (PostModel post : postsModel) {
            if (post.getQtdDenuncia() < 5 && post.getBlockDenuncia() != 1) {
                Optional<GrupoModel> grupo = Optional.empty();
                Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

                UsuarioModel autor = usuarioRepository.findByLogin(post.getLoginAutor());
                Optional<PerfilUsuarioModel> perfilAutor = perfilUsuarioRepository.findById(autor.getIdPerfilUsuario());

                if (post.getIdGrupo() != null) {
                    grupo = grupoRepository.findById(post.getIdGrupo());
                    if (grupo.isPresent()) {
                        perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                    }
                }

                postsDTO.add(new RespostaPostDto(
                        post.getIdPost(),
                        perfilUsuarioService.selecionarNomeExibido(autor),
                        perfilAutor.get().getUrlFotoPerfil(),
                        grupo.isPresent() ? grupo.get().getNome() : null,
                        perfilGrupo.isPresent() ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.getUrlMidia(),
                        post.getConteudo(),
                        post.momentoFormatado(),
                        post.getLoginAutor(),
                        post.getQtdLike(),
                        post.getQtdComentarios(),
                        postCurtidoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getTagRelatorio(),
                        autor.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR),
                        postDenunciadoPeloUsuario(usuario.getLogin(), post.getIdPost()),
                        post.getBlockDenuncia()
                ));
            }
        }
        return postsDTO;
    }

    public Boolean postCurtidoPeloUsuario(String login, UUID idPost) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        if (perfil.isPresent()) {
            return perfil.get().getIdPostsCurtidos().contains(idPost);
        }
        return null;
    }

    public Boolean postDenunciadoPeloUsuario(String login, UUID idPost) {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

        if (perfil.isPresent()) {
            return perfil.get().getIdPostsDenunciados().contains(idPost);
        }
        return null;
    }
}


