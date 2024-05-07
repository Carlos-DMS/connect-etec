package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.PerfilGrupoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerfilGrupoRepository extends JpaRepository<PerfilGrupoModel, UUID> {
}
