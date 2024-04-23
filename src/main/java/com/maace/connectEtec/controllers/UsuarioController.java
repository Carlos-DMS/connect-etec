package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.UsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioDto usuarioDto){

        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuario);
        service.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos(){

        List<UsuarioModel> usuarios = service.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

//    @GetMapping("/loginUsuario")
//    public ResponseEntity<Boolean> loginUsuario(@RequestParam String login, @RequestParam String senha){
//
//        boolean valido = service.loginUsuario(login, senha);
//
//        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
//
//        return ResponseEntity.status(status).body(valido);
//    }
//
//    @GetMapping("/validarUsuario")
//    public ResponseEntity<UsuarioModel> validarUsuario(@RequestParam String login, @RequestParam Integer token) {
//        UsuarioModel usuario = service.validarUsuario(login, token);
//
//        if (usuario == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.OK).body(usuario);
//        }
//    }
}
