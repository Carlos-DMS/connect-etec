package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.requests.AlunoRequestDto;
import com.maace.connectEtec.dtos.responses.AlunoResponseDto;
import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.services.AlunoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl alunoService;

    @PostMapping("/salvar")
    public ResponseEntity<AlunoResponseDto> salvar(@RequestBody @Valid AlunoRequestDto alunoDto){

        AlunoModel aluno = new AlunoModel();
        BeanUtils.copyProperties(alunoDto, aluno);
        alunoService.salvar(aluno);
        AlunoResponseDto response = alunoService.converterParaResponseDto(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<AlunoResponseDto>> listarTodos(){

        List<AlunoModel> alunos = alunoService.listarTodos();
        List<AlunoResponseDto> listaResponse = new ArrayList<>();

        for (AlunoModel aluno : alunos) {
            listaResponse.add(alunoService.converterParaResponseDto(aluno));
        }

        return ResponseEntity.status(HttpStatus.OK).body(listaResponse);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarUsuario(@RequestParam String login, @RequestParam String senha){

        boolean valido = alunoService.validarUsuario(login, senha);

        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valido);
    }
}
