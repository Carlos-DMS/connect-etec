package com.maace.connectEtec.services;

import java.util.List;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public abstract class UsuarioService<T extends UsuarioModel> {

    protected UsuarioRepository<T> repository;
    protected PasswordEncoder encoder;

    @Autowired
    public UsuarioService(PasswordEncoder encoder, UsuarioRepository<T> repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public void salvar(T usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<T> listarTodos() {
        return repository.findAll();
    }

    public boolean validarUsuario(String login, String senha) {
        Optional<T> usuario = repository.findByLogin(login);
        return usuario.map(u -> encoder.matches(senha, u.getSenha())).orElse(false);
    }
}


