package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    UsuarioModel findByLogin(String login);
    @Query("SELECT u FROM UsuarioModel u WHERE LOWER(u.nomeCompleto) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<UsuarioModel> findByNomeCompletoContaining(@Param("nome") String nome);

    @Query("SELECT u FROM UsuarioModel u WHERE LOWER(u.nomeSocial) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<UsuarioModel> findByNomeSocialContaining(@Param("nome") String nome);

}
