package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    UsuarioModel findByLogin(String login);
}
