package com.maace.connectEtec.services;

import com.maace.connectEtec.models.FuncionarioModel;
import com.maace.connectEtec.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void salvar(FuncionarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        funcionarioRepository.save(usuario);
    }

    public List<FuncionarioModel> listarTodos(){
        return funcionarioRepository.findAll();
    }

    public boolean validarUsuario(String loginInserido, String senhaInserida) {
        Optional<FuncionarioModel> optUsuario = funcionarioRepository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return false;
        }
        else {
            return encoder.matches(senhaInserida, optUsuario.get().getSenha());
        }
    }
}
