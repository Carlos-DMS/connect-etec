package com.maace.connectEtec.services;

import com.maace.connectEtec.models.FuncionarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService extends UsuarioService<FuncionarioModel> {

    @Autowired
    public FuncionarioService(PasswordEncoder encoder, UsuarioRepository<FuncionarioModel> repository) {
        super(encoder, repository);
    }
}
