package com.maace.connectEtec.services;

import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void salvar(AlunoModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        alunoRepository.save(usuario);
    }

    public List<AlunoModel> listarTodos(){
        return alunoRepository.findAll();
    }

    public boolean validarUsuario(String loginInserido, String senhaInserida) {
        Optional<AlunoModel> optUsuario = alunoRepository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return false;
        }
        else {
            return encoder.matches(senhaInserida, optUsuario.get().getSenha());
        }
    }
}