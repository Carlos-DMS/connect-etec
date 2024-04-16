package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository<T extends UsuarioModel> extends JpaRepository<T, UUID> {
    Optional<T> findByLogin(String login);
}


