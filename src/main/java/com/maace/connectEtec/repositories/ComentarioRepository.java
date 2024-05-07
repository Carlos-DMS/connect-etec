package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ComentarioRepository extends JpaRepository<ComentarioModel, UUID> { }
