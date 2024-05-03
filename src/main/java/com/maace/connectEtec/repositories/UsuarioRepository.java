package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    UsuarioModel findByLogin(String login);
}
