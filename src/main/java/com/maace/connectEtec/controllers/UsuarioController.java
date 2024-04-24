package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.LoginUsuarioDto;
import com.maace.connectEtec.dtos.CadastroUsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid CadastroUsuarioDto cadastroUsuarioDto){
        if(service.loadUserByUsername(cadastroUsuarioDto.login()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(cadastroUsuarioDto, usuario);
        service.cadastrar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginUsuarioDto loginUsuarioDto) {
        var emailSenha = new UsernamePasswordAuthenticationToken(loginUsuarioDto.login(), loginUsuarioDto.senha());
        var auth = this.authenticationManager.authenticate(emailSenha);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos(){

        List<UsuarioModel> usuarios = service.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }


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
