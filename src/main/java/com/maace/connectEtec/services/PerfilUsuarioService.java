package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.perfilUsuario.EditarFotoPerfilDto;
import com.maace.connectEtec.dtos.perfilUsuario.EditarPerfilUsuarioDto;
import com.maace.connectEtec.dtos.perfilUsuario.AcessarPerfilUsuarioDto;
import com.maace.connectEtec.dtos.perfilUsuario.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PerfilUsuarioService {

    @Autowired
    PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PerfilGrupoRepository perfilGrupoRepository;

    public void criarPerfilUsuario(PerfilUsuarioModel perfilUsuario) {
        perfilUsuarioRepository.save(perfilUsuario);
    }

    public void editarDadosPerfil(EditarPerfilUsuarioDto atualizarPerfilDto, UsuarioModel usuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(usuario.getLogin());

        usuario.setNomeCompleto(atualizarPerfilDto.nomeCompleto());
        usuario.setNomeSocial(atualizarPerfilDto.nomeSocial());

        perfil.get().setSobre(atualizarPerfilDto.sobre());

        usuarioRepository.save(usuario);
        perfilUsuarioRepository.save(perfil.get());
    }

    public void editarFotoPerfil(EditarFotoPerfilDto fotoDto, UsuarioModel usuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(usuario.getLogin());

        perfil.get().setUrlFotoPerfil(fotoDto.urlFotoPerfil());

        perfilUsuarioRepository.save(perfil.get());
    }

    public List<Optional<RespostaPerfilUsuarioDto>> listarPerfis(UsuarioModel usuarioLogado) {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Optional<RespostaPerfilUsuarioDto>> perfis = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(idPerfilUsuario);

            if (perfil.get().getIdPerfil() != usuarioLogado.getIdPerfilUsuario()) {
                perfis.add(Optional.of(new RespostaPerfilUsuarioDto
                        (selecionarNomeExibido(usuario),
                                perfil.get().getUrlFotoPerfil(),
                                usuario.getLogin(),
                                perfilSeguidoPeloUsuarioLogado(
                                        usuarioLogado,
                                        usuario.getLogin()
                                )))
                );
            }
        }
        return perfis;
    }

    public AcessarPerfilUsuarioDto acessarPerfilUsuario(String loginUsuario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        if (usuario != null) {
            return new AcessarPerfilUsuarioDto(
                    usuario.getNomeCompleto(),
                    usuario.getNomeSocial(),
                    selecionarNomeExibido(usuario),
                    perfil.get().getUrlFotoPerfil(),
                    perfil.get().getSobre(),
                    perfil.get().getQtdUsuariosSeguidos(),
                    perfil.get().getQtdSeguidores(),
                    null,
                    usuario.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR)
            );
        }
        return null;
    }

    //Sobrecarga para /perfilUsuario/buscarPerfil
    public AcessarPerfilUsuarioDto acessarPerfilUsuario(
            UsuarioModel usuarioLogado,
            String loginUsuario)
    {
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        if (usuario != null) {
            return new AcessarPerfilUsuarioDto(
                    usuario.getNomeCompleto(),
                    usuario.getNomeSocial(),
                    selecionarNomeExibido(usuario),
                    perfil.get().getUrlFotoPerfil(),
                    perfil.get().getSobre(),
                    perfil.get().getQtdUsuariosSeguidos(),
                    perfil.get().getQtdSeguidores(),
                    perfilSeguidoPeloUsuarioLogado(usuarioLogado, loginUsuario),
                    usuario.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR)
            );
        }
        return null;
    }

    public List<Optional<RespostaPostDto>> buscarPosts(String login) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(login);

        List<UUID> idPosts = perfilUsuario.get().getIdPosts();

        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        for (UUID idPost : idPosts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            Optional<PostModel> post = postRepository.findById(idPost);

            if (post.isPresent()) {
                UsuarioModel usuarioAutor = usuarioRepository.findByLogin(post.get().getLoginAutor());

                if (post.get().getIdGrupo() != null) {
                    grupo = grupoRepository.findById(post.get().getIdGrupo());
                    perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                }

                posts.add(Optional.of(new RespostaPostDto(
                        post.get().getIdPost(),
                        selecionarNomeExibido(usuarioAutor),
                        perfilUsuario.get().getUrlFotoPerfil(),
                        (grupo.isPresent()) ? grupo.get().getNome() : null,
                        (perfilGrupo.isPresent()) ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.get().getUrlMidia(),
                        post.get().getConteudo(),
                        post.get().momentoFormatado(),
                        usuarioAutor.getLogin(),
                        post.get().getQtdLike(),
                        post.get().getQtdComentarios(),
                        postCurtidoPeloUsuario(login, post.get().getIdPost()),
                        post.get().getTagRelatorio(),
                        usuarioAutor.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR)
                )));
            }
        }
        Collections.reverse(posts);

        return posts;
    }

    //Sobrecarga para o endpoint /perfilUsuario/buscarPosts
    public List<Optional<RespostaPostDto>> buscarPosts(String loginAutor, String loginUsuario) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(loginAutor);

        List<UUID> idPosts = perfilUsuario.get().getIdPosts();

        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        for (UUID idPost : idPosts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            Optional<PostModel> post = postRepository.findById(idPost);

            if (post.isPresent()) {
                UsuarioModel usuarioAutor = usuarioRepository.findByLogin(post.get().getLoginAutor());

                if (post.get().getIdGrupo() != null) {
                    grupo = grupoRepository.findById(post.get().getIdGrupo());
                    perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
                }

                posts.add(Optional.of(new RespostaPostDto(
                        post.get().getIdPost(),
                        selecionarNomeExibido(usuarioAutor),
                        perfilUsuario.get().getUrlFotoPerfil(),
                        (grupo.isPresent()) ? grupo.get().getNome() : null,
                        (perfilGrupo.isPresent()) ? perfilGrupo.get().getUrlFotoPerfil() : null,
                        post.get().getUrlMidia(),
                        post.get().getConteudo(),
                        post.get().momentoFormatado(),
                        usuarioAutor.getLogin(),
                        post.get().getQtdLike(),
                        post.get().getQtdComentarios(),
                        postCurtidoPeloUsuario(loginUsuario, post.get().getIdPost()),
                        post.get().getTagRelatorio(),
                        usuarioAutor.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR)
                )));
            }
        }
        Collections.reverse(posts);

        return posts;
    }

    public List<RespostaPerfilGrupoDto> buscarGrupos(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        List<UUID> idGrupos = perfil.get().getGrupos();

        List<RespostaPerfilGrupoDto> grupos = new ArrayList<>();

        for (UUID idGrupo : idGrupos) {
            Optional<GrupoModel> grupo = grupoRepository.findById(idGrupo);

            String urlFotoDePerfil = perfilGrupoRepository.findById(grupo.get().getIdGrupo()).get().getUrlFotoPerfil();

            grupos.add(new RespostaPerfilGrupoDto(idGrupo.toString(), grupo.get().getNome(), urlFotoDePerfil));
        }
        return grupos;
    }

    public Boolean seguirUsuario(String loginUsuario,
                                 String loginUsuarioSeguido,
                                 boolean estaSeguido) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(loginUsuario);
        Optional<PerfilUsuarioModel> perfilUsuarioSeguido = buscarPerfil(loginUsuarioSeguido);

        if (perfilUsuario.isPresent() && perfilUsuarioSeguido.isPresent()) {
            if (!estaSeguido) {
                perfilUsuario.get().adicionarLoginUsuarioSeguido(loginUsuarioSeguido);
                perfilUsuarioSeguido.get().adicionarLoginSeguidor(loginUsuario);

                perfilUsuarioRepository.save(perfilUsuario.get());
                perfilUsuarioRepository.save(perfilUsuarioSeguido.get());

                return true;
            }
            perfilUsuario.get().removerLoginUsuarioSeguido(loginUsuarioSeguido);
            perfilUsuarioSeguido.get().removerLoginSeguidor(loginUsuario);

            perfilUsuarioRepository.save(perfilUsuario.get());
            perfilUsuarioRepository.save(perfilUsuarioSeguido.get());

            return false;
        }
        return null;
    }

    public List<RespostaPerfilUsuarioDto> listarUsuariosSeguidos(UsuarioModel usuario) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(usuario.getLogin());

        List<RespostaPerfilUsuarioDto> usuariosSeguidos = new ArrayList<>();

        if (perfilUsuario.isPresent()) {
            for (String loginUsuarioSeguido : perfilUsuario.get().getLoginUsuariosSeguidos()) {
                UsuarioModel usuarioSeguido = usuarioRepository.findByLogin(loginUsuarioSeguido);
                Optional<PerfilUsuarioModel> perfilUsuarioSeguido = perfilUsuarioRepository.findById(usuarioSeguido.getIdPerfilUsuario());

                if (perfilUsuarioSeguido.isPresent()) {
                    usuariosSeguidos.add(new RespostaPerfilUsuarioDto(
                            selecionarNomeExibido(usuarioSeguido),
                            perfilUsuarioSeguido.get().getUrlFotoPerfil(),
                            usuarioSeguido.getLogin(),
                            perfilSeguidoPeloUsuarioLogado(usuario, usuarioSeguido.getLogin())
                    ));
                }
            }
            return usuariosSeguidos;
        }
        return null;
    }

    //Sobrecarga /perfilUsuario/usuariosSeguidos
    public List<RespostaPerfilUsuarioDto> listarUsuariosSeguidos(UsuarioModel usuarioLogado, String loginUsuarioPesquisado) {
        Optional<PerfilUsuarioModel> perfilUsuarioPesquisado = buscarPerfil(loginUsuarioPesquisado);

        List<RespostaPerfilUsuarioDto> usuariosSeguidos = new ArrayList<>();

        if (perfilUsuarioPesquisado.isPresent()) {
            for (String loginUsuarioSeguido : perfilUsuarioPesquisado.get().getLoginUsuariosSeguidos()) {
                UsuarioModel usuarioSeguido = usuarioRepository.findByLogin(loginUsuarioSeguido);
                Optional<PerfilUsuarioModel> perfilUsuarioSeguido = perfilUsuarioRepository.findById(usuarioSeguido.getIdPerfilUsuario());

                if (perfilUsuarioSeguido.isPresent()) {
                    usuariosSeguidos.add(new RespostaPerfilUsuarioDto(
                            selecionarNomeExibido(usuarioSeguido),
                            perfilUsuarioSeguido.get().getUrlFotoPerfil(),
                            usuarioSeguido.getLogin(),
                            perfilSeguidoPeloUsuarioLogado(usuarioLogado, usuarioSeguido.getLogin())
                    ));
                }
            }
            return usuariosSeguidos;
        }
        return null;
    }

    public List<RespostaPerfilUsuarioDto> listarSeguidores(UsuarioModel usuario) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(usuario.getLogin());

        List<RespostaPerfilUsuarioDto> seguidores = new ArrayList<>();

        if (perfilUsuario.isPresent()) {
            for (String loginUsuarioSeguido : perfilUsuario.get().getLoginSeguidores()) {
                UsuarioModel seguidor = usuarioRepository.findByLogin(loginUsuarioSeguido);
                Optional<PerfilUsuarioModel> perfilSeguidor = perfilUsuarioRepository.findById(seguidor.getIdPerfilUsuario());

                if (perfilSeguidor.isPresent()) {
                    seguidores.add(new RespostaPerfilUsuarioDto(
                            selecionarNomeExibido(seguidor),
                            perfilSeguidor.get().getUrlFotoPerfil(),
                            seguidor.getLogin(),
                            perfilSeguidoPeloUsuarioLogado(usuario, seguidor.getLogin())
                    ));
                }
            }
            return seguidores;
        }
        return null;
    }

    //Sobrecarga para /perfilUsuario/seguidores
    public List<RespostaPerfilUsuarioDto> listarSeguidores(UsuarioModel usuarioLogado, String loginUsuarioPesquisado) {
        Optional<PerfilUsuarioModel> perfilUsuarioPesquisado = buscarPerfil(loginUsuarioPesquisado);

        List<RespostaPerfilUsuarioDto> seguidores = new ArrayList<>();

        if (perfilUsuarioPesquisado.isPresent()) {
            for (String loginUsuarioSeguido : perfilUsuarioPesquisado.get().getLoginSeguidores()) {
                UsuarioModel seguidor = usuarioRepository.findByLogin(loginUsuarioSeguido);
                Optional<PerfilUsuarioModel> perfilSeguidor = perfilUsuarioRepository.findById(seguidor.getIdPerfilUsuario());

                if (perfilSeguidor.isPresent()) {
                    seguidores.add(new RespostaPerfilUsuarioDto(
                            selecionarNomeExibido(seguidor),
                            perfilSeguidor.get().getUrlFotoPerfil(),
                            seguidor.getLogin(),
                            perfilSeguidoPeloUsuarioLogado(usuarioLogado, seguidor.getLogin())
                    ));
                }
            }
            return seguidores;
        }
        return null;
    }

    public List<RespostaPerfilUsuarioDto> buscarUsuariosPorNome(
            UsuarioModel usuarioLogado,
            String nome)
    {
        List<RespostaPerfilUsuarioDto> perfisDTO = new ArrayList<>();

        List<UsuarioModel> usuarios = usuarioRepository.findByNomeCompletoContaining(nome);
        List<UsuarioModel> usuariosNomeSocial = usuarioRepository.findByNomeSocialContaining(nome);

        for (UsuarioModel usuarioNomeSocial : usuariosNomeSocial) {
            if (!usuarios.contains(usuarioNomeSocial)) {
                usuarios.add(usuarioNomeSocial);
            }
        }

        usuarios.sort(Comparator.comparing(UsuarioModel::getNomeCompleto, String.CASE_INSENSITIVE_ORDER));

        for (UsuarioModel usuario : usuarios) {
            Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioRepository.findById(usuario.getIdPerfilUsuario());

            if (perfilUsuario.isPresent()) {
                perfisDTO.add(new RespostaPerfilUsuarioDto(
                        selecionarNomeExibido(usuario),
                        perfilUsuario.get().getUrlFotoPerfil(),
                        usuario.getLogin(),
                        !usuario.equals(usuarioLogado) ? perfilSeguidoPeloUsuarioLogado(usuarioLogado, usuario.getLogin()) : null
                ));
            }
        }
        return perfisDTO;
    }

    public Optional<PerfilUsuarioModel> buscarPerfil(String loginUsuario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);

        UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

        return perfilUsuarioRepository.findById(idPerfilUsuario);
    }

    public String selecionarNomeExibido(UsuarioModel usuario) {
        String nome;

        if (usuario.getNomeSocial() != null) {
            nome = usuario.getNomeSocial();
        } else {
            nome = usuario.getNomeCompleto();
        }
        return nome;
    }

    public Boolean perfilSeguidoPeloUsuarioLogado(UsuarioModel usuarioLogado, String loginPerfil) {
        Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(usuarioLogado.getIdPerfilUsuario());

        if (perfil.isPresent() && !usuarioLogado.getLogin().equals(loginPerfil)) {
            return perfil.get().getLoginUsuariosSeguidos().contains(loginPerfil);
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
