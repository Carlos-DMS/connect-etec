package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.RespostaUsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import com.maace.connectEtec.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public void cadastrar(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<RespostaUsuarioDto> listarTodos() {
        List<UsuarioModel> usuarios = repository.findAll();
        List<RespostaUsuarioDto> usuariosDto = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            RespostaUsuarioDto respostaUsuarioDto = new RespostaUsuarioDto(
                    usuario.getLogin(),
                    usuario.getNomeCompleto(),
                    usuario.getNomeSocial(),
                    usuario.getTipoUsuario().getRole());
            usuariosDto.add(respostaUsuarioDto);
        }
        return usuariosDto;
    }

    public UsuarioModel buscarPorToken(String authorizationHeader) {
        String token = extrairToken(authorizationHeader);
        if (token != null) {
            String login = tokenService.validarToken(token);
            return repository.findByLogin(login);
        }
        return null;
    }

    private String extrairToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
