package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.perfilGrupo.AcessarPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarDadosPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.EditarFotoPerfilGrupoDto;
import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.dtos.post.RespostaPostDto;
import com.maace.connectEtec.models.*;
import com.maace.connectEtec.repositories.GrupoRepository;
import com.maace.connectEtec.repositories.PerfilGrupoRepository;
import com.maace.connectEtec.repositories.PostRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PerfilGrupoService {

    @Autowired
    PerfilGrupoRepository repository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    UsuarioService usuarioService;

    public Optional<RespostaPerfilGrupoDto> buscarPorId(UUID id){
        Optional<GrupoModel> grupo = grupoRepository.findById(id);

        if(grupo.isPresent()){
            Optional<PerfilGrupoModel> perfil = repository.findById(grupo.get().getIdPerfilGrupo());
            RespostaPerfilGrupoDto perfilDto = new RespostaPerfilGrupoDto(grupo.get().getNome(), perfil.get().getUrlFotoPerfil());

            return Optional.of(perfilDto);
        }

        return Optional.empty();
    }

    public List<RespostaPerfilGrupoDto> listarTodos() {
        List<GrupoModel> grupos = grupoRepository.findAll();
        List<RespostaPerfilGrupoDto> perfisDto = new ArrayList<>();

        for(GrupoModel grupo : grupos){
            Optional<PerfilGrupoModel> perfil = repository.findById(grupo.getIdPerfilGrupo());
            perfisDto.add(new RespostaPerfilGrupoDto(grupo.getNome(), perfil.get().getUrlFotoPerfil()));
        }

        return perfisDto;
    }

    public boolean editarDados(EditarDadosPerfilGrupoDto perfilGrupoDto, UsuarioModel usuario){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(perfilGrupoDto.idGrupo()));
        Optional<PerfilGrupoModel> perfilOptional = repository.findById(grupo.get().getIdPerfilGrupo());


        if (perfilOptional.isPresent()) {
            if(grupo.get().getLoginModeradores().contains(usuario.getLogin()) || grupo.get().getLoginDono().equals(usuario.getLogin())){

                PerfilGrupoModel perfil = perfilOptional.get();

                grupo.get().setNome(perfilGrupoDto.nome());
                perfilOptional.get().setSobre(perfilGrupoDto.sobre());

                repository.save(perfil);
                grupoRepository.save(grupo.get());

                return true;
            }
        }

        return false;
    }

    public boolean editarFotoPerfil(EditarFotoPerfilGrupoDto fotoDto, String usuarioToken){
        Optional<GrupoModel> grupo = grupoRepository.findById(UUID.fromString(fotoDto.idGrupo()));
        Optional<PerfilGrupoModel> perfilOptional = repository.findById(grupo.get().getIdPerfilGrupo());
        UsuarioModel usuario = usuarioService.buscarPorToken(usuarioToken);

        if (perfilOptional.isPresent()) {
            if(grupo.get().getLoginModeradores().contains(usuario.getLogin()) || grupo.get().getLoginDono().equals(usuario.getLogin())){

                PerfilGrupoModel perfil = perfilOptional.get();
                perfil.setUrlFotoPerfil(fotoDto.urlFotoPerfil());

                repository.save(perfil);
                grupoRepository.save(grupo.get());

                return true;
            }
        }


        return false;
    }

    public AcessarPerfilGrupoDto acessarPerfilGrupo(UUID id){
        Optional<GrupoModel> grupo = grupoRepository.findById(id);
        Optional<PerfilGrupoModel> perfil = repository.findById(id);

        if(grupo.isPresent()){
            return new AcessarPerfilGrupoDto(
                    grupo.get().getNome(),
                    perfil.get().getUrlFotoPerfil(),
                    perfil.get().getSobre()
            );
        }

        return null;
    }

    public List<Optional<RespostaPostDto>> buscarPosts(UUID id){
        Optional<PerfilGrupoModel> perfilGrupo = repository.findById(id);
        Optional<GrupoModel> grupo = grupoRepository.findById(id);

        if(grupo.isEmpty() || perfilGrupo.isEmpty()) return null;

        List<UUID> idPosts = perfilGrupo.get().getIdPosts();
        List<Optional<RespostaPostDto>> posts = new ArrayList<>();

        for(UUID idPost : idPosts){
            Optional<PostModel> post = postRepository.findById(idPost);
            Optional<PerfilUsuarioModel> perfilUsuario = perfilUsuarioService.buscarPerfil(post.get().getLoginAutor());
            UsuarioModel usuario = usuarioRepository.findByLogin(post.get().getLoginAutor());

            posts.add(Optional.of(new RespostaPostDto(
                    post.get().getIdPost(),
                    perfilUsuarioService.selecionarNomeExibido(usuario),
                    perfilUsuario.get().getUrlFotoPerfil(),
                    grupo.get().getNome(),
                    perfilGrupo.get().getUrlFotoPerfil(),
                    post.get().getUrlMidia(),
                    post.get().getMomentoPublicacao(),
                    post.get().getConteudo(),
                    post.get().getQtdLike(),
                    post.get().getTagRelatorio()
            )));
        }

        return null;
    }
}
