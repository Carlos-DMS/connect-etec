package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.*;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.security.TokenService;
import com.maace.connectEtec.services.PerfilUsuarioService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid CadastroUsuarioDto cadastroUsuarioDto) {
        if (usuarioService.loadUserByUsername(cadastroUsuarioDto.login()) == null) {
            UsuarioModel usuario = new UsuarioModel();
            PerfilUsuarioModel perfil = new PerfilUsuarioModel();

            BeanUtils.copyProperties(cadastroUsuarioDto, usuario);

            perfilUsuarioService.criarPerfilUsuario(perfil);

            usuario.setIdPerfilUsuario(perfil.getIdPerfil());

            usuarioService.cadastrar(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginUsuarioDto loginUsuarioDto) {
        var emailSenha = new UsernamePasswordAuthenticationToken(loginUsuarioDto.login(), loginUsuarioDto.senha());
        var auth = this.authenticationManager.authenticate(emailSenha);

        var token = tokenService.gerarToken((UsuarioModel) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/validarUsuario")
    public ResponseEntity<ValidarUsuarioDto> validarUsuario(@RequestHeader("Authorization") String authorizationHeader) {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);
        if (usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ValidarUsuarioDto(usuario.getNomeCompleto(),
                    usuario.getNomeSocial(), usuario.getTipoUsuario()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/recuperarConta")
    public ResponseEntity<UUID> recuperarConta(@RequestBody @Valid RecuperarContaDto contaDto) {
        UUID resposta = usuarioService.recuperarConta(contaDto.login());

        if (resposta != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resposta);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/mudarSenha")
    public ResponseEntity mudarSenha(@RequestBody @Valid MudarSenhaDto mudarSenhaDto) {
        boolean resultado = usuarioService.mudarSenha(
                UUID.fromString(mudarSenhaDto.idRequest()),
                mudarSenhaDto.numeroDeRecuperacao(),
                mudarSenhaDto.login(),
                mudarSenhaDto.senha()
        );

        if (resultado) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
