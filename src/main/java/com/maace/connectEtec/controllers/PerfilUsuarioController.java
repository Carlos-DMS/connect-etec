package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.perfilUsuario.CadastroPerfilUsuarioDto;
import com.maace.connectEtec.dtos.perfilUsuario.RespostaPerfilUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.services.PerfilUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfilUsuario")
public class PerfilUsuarioController {

    @Autowired
    private PerfilUsuarioService service;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody @Valid CadastroPerfilUsuarioDto cadastroPerfilUsuarioDto){
        PerfilUsuarioModel perfilUsuarioModel = new PerfilUsuarioModel();
        BeanUtils.copyProperties(cadastroPerfilUsuarioDto, perfilUsuarioModel);
        service.criarPerfilUsuario(perfilUsuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listaPerfis")
    public ResponseEntity<List<Optional<RespostaPerfilUsuarioDto>>> listaPerfis(){
        List<Optional<RespostaPerfilUsuarioDto>> perfis = service.listarPerfis();
        if (perfis != null)  return ResponseEntity.status(HttpStatus.OK).body(perfis);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarPerfil")
    public ResponseEntity buscarPorId(@RequestBody String login) {
        Optional<PerfilUsuarioModel> perfilUsuario = service.buscarPerfil(login);
        if (perfilUsuario.isPresent()) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
