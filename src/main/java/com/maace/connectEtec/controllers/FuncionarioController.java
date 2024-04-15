package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.FuncionarioDto;
import com.maace.connectEtec.models.FuncionarioModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController extends UsuarioModel {
    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/salvar")
    public ResponseEntity<FuncionarioModel> salvar(@RequestBody @Valid FuncionarioDto funcionarioDto){

        FuncionarioModel usuario = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioDto, usuario);
        funcionarioService.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<FuncionarioModel>> listarTodos(){

        List<FuncionarioModel> usuarios = funcionarioService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarUsuario(@RequestParam String login, @RequestParam String senha){

        boolean valido = funcionarioService.validarUsuario(login, senha);

        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valido);
    }
}
