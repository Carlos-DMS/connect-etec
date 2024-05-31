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

    public List<Optional<RespostaPerfilUsuarioDto>> listarPerfis(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Optional<RespostaPerfilUsuarioDto>> perfis = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(idPerfilUsuario);

            perfis.add(Optional.of(new RespostaPerfilUsuarioDto(selecionarNomeExibido(usuario), perfil.get().getUrlFotoPerfil(), usuario.getLogin())));
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
                    perfil.get().getSobre()
            );
        }
        return null;
    }

    public List<Optional<RespostaPostDto>> buscarPosts(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(loginUsuario);

        List<UUID> idPosts = perfilUsuario.get().getIdPosts();

        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        for (UUID idPost : idPosts) {
            Optional<GrupoModel> grupo = Optional.empty();
            Optional<PerfilGrupoModel> perfilGrupo = Optional.empty();

            Optional<PostModel> post = postRepository.findById(idPost);

            UsuarioModel usuario = usuarioRepository.findByLogin(post.get().getLoginAutor());

            if (post.get().getIdGrupo() != null) {
                grupo = grupoRepository.findById(post.get().getIdGrupo());
                perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());
            }
            
            posts.add(Optional.of(new RespostaPostDto(
                    post.get().getIdPost(),
                    selecionarNomeExibido(usuario),
                    perfilUsuario.get().getUrlFotoPerfil(),
                    (grupo.isPresent()) ? grupo.get().getNome() : null,
                    (perfilGrupo.isPresent()) ? perfilGrupo.get().getUrlFotoPerfil() : null,
                    post.get().getUrlMidia(),
                    post.get().getMomentoPublicacao(),
                    post.get().getConteudo(),
                    post.get().getQtdLike(),
                    post.get().getTagRelatorio()
            )));
        }
        Collections.reverse(posts);

        return posts;
    }

    public List<RespostaPerfilUsuarioDto> buscarConexoes(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        List<String> loginConexoes = perfil.get().getLoginConexoes();

        List<RespostaPerfilUsuarioDto> conexoes = new ArrayList<>();

        if (loginConexoes != null) {
            for (String loginConexao : loginConexoes) {
                UsuarioModel usuario = usuarioRepository.findByLogin(loginConexao);

                Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(usuario.getLogin());

                conexoes.add(new RespostaPerfilUsuarioDto(selecionarNomeExibido(usuario), perfil.get().getUrlFotoPerfil(), usuario.getLogin()));
            }
            return conexoes;
        }
        return null;
    }

    public List<RespostaPerfilGrupoDto> buscarGrupos (String loginUsuario) {
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

    public Optional<PerfilUsuarioModel> buscarPerfil(String loginUsuario){
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);

        UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

        return perfilUsuarioRepository.findById(idPerfilUsuario);
    }

    public String selecionarNomeExibido(UsuarioModel usuario) {
        String nome;

        if (usuario.getNomeSocial() != null) {
            nome = usuario.getNomeSocial();
        }
        else{
            nome = usuario.getNomeCompleto();
        }

        return nome;
    }
}
