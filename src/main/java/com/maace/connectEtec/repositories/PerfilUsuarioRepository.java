package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.PerfilUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuarioModel, UUID> {
    PerfilUsuarioModel findById(String login);
}
