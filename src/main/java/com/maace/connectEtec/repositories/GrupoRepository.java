package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.GrupoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GrupoRepository extends JpaRepository<GrupoModel, UUID> {
}
