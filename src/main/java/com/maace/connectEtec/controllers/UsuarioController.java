package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.UsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class
UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioDto usuarioDto){
        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuario);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String senha){
        boolean valido = false;
        Optional<UsuarioModel> optUsuario = usuarioRepository.findByLogin(login);
        if (optUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(valido);
        }

        UsuarioModel usuario = optUsuario.get();
        valido = encoder.matches(senha, usuario.getSenha());

        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valido);
    }
}
