package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.responses.AlunoResponseDto;
import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void salvar(AlunoModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        alunoRepository.save(usuario);
    }

    @Override
    public List<AlunoModel> listarTodos(){
        return alunoRepository.findAll();
    }

    @Override
    public boolean validarUsuario(String loginInserido, String senhaInserida) {
        Optional<AlunoModel> optUsuario = alunoRepository.findByLogin(loginInserido);

        if (optUsuario.isEmpty()) {
            return false;
        }
        else {
            return encoder.matches(senhaInserida, optUsuario.get().getSenha());
        }
    }

    @Override
    public AlunoResponseDto converterParaResponseDto(AlunoModel usuario) {
        return new AlunoResponseDto(usuario.getLogin(), usuario.getNomeCompleto(), usuario.getNomeSocial(), usuario.getCurso());
    }


}
