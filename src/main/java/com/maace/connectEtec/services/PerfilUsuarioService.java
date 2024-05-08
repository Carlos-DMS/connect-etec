package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.AcessarPerfilUsuarioDto;
import com.maace.connectEtec.dtos.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.dtos.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void criarPerfilUsuario(PerfilUsuarioModel perfilUsuario){
        perfilUsuarioRepository.save(perfilUsuario);
    }

    public List<Optional<RespostaPerfilUsuarioDto>> listarPerfis(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Optional<RespostaPerfilUsuarioDto>> perfis = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(idPerfilUsuario);

            perfis.add(Optional.of(new RespostaPerfilUsuarioDto(selecionarNomeExibido(usuario), perfil.get().getUrlFotoPerfil())));
        }
        return perfis;
    }

    public AcessarPerfilUsuarioDto acessarPerfilUsuario(String loginUsuario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        return new AcessarPerfilUsuarioDto(selecionarNomeExibido(usuario), perfil.get().getUrlFotoPerfil(), perfil.get().getSobre());
    }

    public List<Optional<RespostaPostDto>> buscarPosts(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(loginUsuario);

        List<UUID> idPosts = perfilUsuario.get().getIdPosts();

        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        for (UUID idPost : idPosts) {
            Optional<PostModel> post = postRepository.findById(idPost);

            UsuarioModel usuario = usuarioRepository.findByLogin(post.get().getLoginAutor());

            Optional<GrupoModel> grupo = grupoRepository.findById(post.get().getIdGrupo());

            Optional<PerfilGrupoModel> perfilGrupo = perfilGrupoRepository.findById(grupo.get().getIdGrupo());

            posts.add(Optional.of(new RespostaPostDto(
                    post.get().getIdPost(),
                    selecionarNomeExibido(usuario),
                    perfilUsuario.get().getUrlFotoPerfil(),
                    grupo.get().getNome(),
                    perfilGrupo.get().getUrlFotoPerfil(),
                    post.get().getUrlMidia(),
                    post.get().getMomentoPublicacao(),
                    post.get().getConteudo(),
                    post.get().getQtdLike(),
                    post.get().getTagsRelatorio()
            )));
        }
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

                conexoes.add(new RespostaPerfilUsuarioDto(selecionarNomeExibido(usuario), perfil.get().getUrlFotoPerfil()));
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

            String nome = grupo.get().getNome();

            String urlFotoDePerfil = perfilGrupoRepository.findById(grupo.get().getIdGrupo()).get().getUrlFotoPerfil();

            grupos.add(new RespostaPerfilGrupoDto(nome, urlFotoDePerfil));
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


    //REALMENTE NECESSARIO FAZER UM UPDATE? SIM!
}
