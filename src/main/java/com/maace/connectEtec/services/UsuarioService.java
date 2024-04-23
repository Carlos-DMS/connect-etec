package com.maace.connectEtec.services;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
