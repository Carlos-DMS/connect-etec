package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.RequestRecuperacaoSenhaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestRecuperacaoSenhaRepository extends JpaRepository<RequestRecuperacaoSenhaModel, UUID> {
}
