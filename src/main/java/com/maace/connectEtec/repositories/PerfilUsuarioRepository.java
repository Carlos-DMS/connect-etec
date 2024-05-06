package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuarioModel, UUID> {
    //Optional<PerfilUsuarioModel> findByLogin(String login);
    PerfilUsuarioModel findById(String login);
}
