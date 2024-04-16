package com.maace.connectEtec.services;

import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunoService extends UsuarioService<AlunoModel> {

    @Autowired
    public AlunoService(PasswordEncoder encoder, UsuarioRepository<AlunoModel> repository) {
        super(encoder, repository);
    }
}
