package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.CadastroPerfilUsuarioDto;
import com.maace.connectEtec.dtos.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.services.PerfilUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/perfilUsuario")
public class PerfilUsuarioController {

    @Autowired
    private PerfilUsuarioService service;

    @PostMapping("salvar")
    public ResponseEntity salvar(@RequestBody @Valid CadastroPerfilUsuarioDto cadastroPerfilUsuarioDto){
        PerfilUsuarioModel perfilUsuarioModel = new PerfilUsuarioModel();
        BeanUtils.copyProperties(cadastroPerfilUsuarioDto, perfilUsuarioModel);

        service.salvarPerfilUsuario(perfilUsuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaPerfilUsuarioDto>> listarTodos(){

        List<RespostaPerfilUsuarioDto> perfilUsuario = service.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(perfilUsuario);
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        PerfilUsuarioModel perfilUsuario = service.buscarPorId(id);
        if (perfilUsuario != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        //BAD_REQUEST ou NOT_FOUND?
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //fazer delete e update?
}
