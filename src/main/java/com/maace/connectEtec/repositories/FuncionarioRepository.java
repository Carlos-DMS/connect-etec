package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID> {

    Optional<FuncionarioModel> findByLogin(String login);

}
