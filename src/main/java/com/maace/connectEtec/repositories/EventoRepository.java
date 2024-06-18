package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<EventoModel, UUID> {
}
