package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.PostModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.GrupoRepository;
import com.maace.connectEtec.repositories.PerfilUsuarioRepository;
import com.maace.connectEtec.repositories.PostRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
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

    public void criarPerfilUsuario(PerfilUsuarioModel perfilUsuario){
        perfilUsuarioRepository.save(perfilUsuario);
    }

    public List<Optional<RespostaPerfilUsuarioDto>> listarPerfis(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Optional<RespostaPerfilUsuarioDto>> perfis = new ArrayList<>();

        String nome;

        for (UsuarioModel usuario : usuarios) {
            UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

            Optional<PerfilUsuarioModel> perfil = perfilUsuarioRepository.findById(idPerfilUsuario);

            if (usuario.getNomeSocial() != null) {
                nome = usuario.getNomeSocial();
            }
            else{
                nome = usuario.getNomeCompleto();
            }

            perfis.add(Optional.of(new RespostaPerfilUsuarioDto(nome, perfil.get().getUrlFotoPerfil())));
        }
        return perfis;
    }

    public Optional<PerfilUsuarioModel> buscarPerfil(String loginUsuario){
        UsuarioModel usuario = usuarioRepository.findByLogin(loginUsuario);

        UUID idPerfilUsuario = usuario.getIdPerfilUsuario();

        return perfilUsuarioRepository.findById(idPerfilUsuario);
    }

    public List<Optional<PostModel>> buscarPosts(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        List<UUID> idPosts = perfil.get().getIdPosts();

        List<Optional<PostModel>> posts = new ArrayList<>();

        for (UUID idPost : idPosts) {
            posts.add(postRepository.findById(idPost)); //usar um dto para resposta de dados do perfil, usuario e post
        }
        return posts;
    }

    public List<RespostaPerfilUsuarioDto> buscarConexoes(String loginUsuario) {
        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);

        List<String> loginConexoes = perfil.get().getLoginConexoes();

        List<RespostaPerfilUsuarioDto> conexoes = new ArrayList<>();

        String nome;

        if (loginConexoes != null) {
            for (String loginConexao : loginConexoes) {
                UsuarioModel usuario = usuarioRepository.findByLogin(loginConexao);

                Optional<PerfilUsuarioModel> perfilUsuario = buscarPerfil(usuario.getLogin());

                if (usuario.getNomeSocial() != null) {
                    nome = usuario.getNomeSocial();
                }
                else{
                    nome = usuario.getNomeCompleto();
                }

                conexoes.add(new RespostaPerfilUsuarioDto(nome, perfil.get().getUrlFotoPerfil()));
            }
            return conexoes;
        }
        return null;
    }

//    public List<GrupoModel> buscarGrupos (String loginUsuario) {
//        Optional<PerfilUsuarioModel> perfil = buscarPerfil(loginUsuario);
//
//        List<UUID> idGrupos = perfil.get().getGrupos();
//
//        for (UUID idGrupo : idGrupos) {
//            grupoRepository.findById(idGrupo).get().getNome();
//
//        }
//
//    }


    //REALMENTE NECESSARIO? FAZER UM UPDATE? SIM!
}
