package com.maace.connectEtec.services;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
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

    public boolean validarUsuario(String loginInserido, String senhaInserida) {
        Optional<UsuarioModel> optUsuario = repository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return false;
        }
        else {
            return encoder.matches(senhaInserida, optUsuario.get().getSenha());
        }
    }
}
