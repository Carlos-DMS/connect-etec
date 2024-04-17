package com.maace.connectEtec.services;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import com.maace.connectEtec.security.TokenUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public void salvar(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<UsuarioModel> listarTodos(){
        return repository.findAll();
    }

    public boolean loginUsuario(String loginInserido, String senhaInserida) {
        Optional<UsuarioModel> optUsuario = repository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return false;
        }
        else {
            UsuarioModel usuario = optUsuario.get();
            if (encoder.matches(senhaInserida, usuario.getSenha())) {
                usuario.setToken(TokenUsuario.gerarToken());
                repository.save(usuario);
                return true;
            } else {
                return false;
            }
        }
    }

    public UsuarioModel validarUsuario(String loginInserido, Integer tokenInserido) {
        Optional<UsuarioModel> optUsuario = repository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return null;
        }
        else {
            UsuarioModel usuario = optUsuario.get();
            if (TokenUsuario.verificarToken(usuario, tokenInserido)){
                return usuario;
            }
            return null;
        }
    }
}
