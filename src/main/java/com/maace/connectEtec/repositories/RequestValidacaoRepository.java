package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.RequestValidacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestValidacaoRepository extends JpaRepository<RequestValidacaoModel, UUID> {
}
