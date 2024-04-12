package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.responses.AlunoResponseDto;
import com.maace.connectEtec.models.AlunoModel;

import java.util.List;

public interface AlunoService {
    void salvar(AlunoModel usuario);
    List<AlunoModel> listarTodos();
    boolean validarUsuario(String loginInserido, String senhaInserida);
    AlunoResponseDto converterParaResponseDto(AlunoModel aluno);
}
