package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.AlunoDto;
import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/salvar")
    public ResponseEntity<AlunoModel> salvar(@RequestBody @Valid AlunoDto alunoDto){

        AlunoModel usuario = new AlunoModel();
        BeanUtils.copyProperties(alunoDto, usuario);
        alunoService.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<AlunoModel>> listarTodos(){

        List<AlunoModel> usuarios = alunoService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarUsuario(@RequestParam String login, @RequestParam String senha){

        boolean valido = alunoService.validarUsuario(login, senha);

        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valido);
    }
}
