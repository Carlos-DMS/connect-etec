package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<AlunoModel, UUID> {

    Optional<AlunoModel> findByLogin(String login);

}
