package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.*;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.security.TokenService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
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

    @PostMapping("/emailValidacao")
    public ResponseEntity<UUID> emailValidacao(@RequestBody @Valid LoginDto loginDto) {
        UUID idRequest = usuarioService.emailCadastro(loginDto.login());

        if (idRequest != null) {
            return ResponseEntity.status(HttpStatus.OK).body(idRequest);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid CadastroUsuarioDto cadastroUsuarioDto) {
        if (usuarioService.loadUserByUsername(cadastroUsuarioDto.login()) == null &&
                usuarioService.cadastrar(
                cadastroUsuarioDto,
                UUID.fromString(cadastroUsuarioDto.idRequest()),
                cadastroUsuarioDto.codigoDeValidacao()
            )
        )
        {
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

    @PostMapping("/recuperarConta")
    public ResponseEntity<UUID> recuperarConta(@RequestBody @Valid LoginDto loginDto) {
        UUID idRequest = usuarioService.recuperarConta(loginDto.login());

        if (idRequest != null) {
            return ResponseEntity.status(HttpStatus.OK).body(idRequest);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/mudarSenha")
    public ResponseEntity mudarSenha(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid MudarSenhaDto mudarSenhaDto
    ) {
        boolean validadeDaOperacao = usuarioService.mudarSenha(
                usuarioService.buscarPorToken(authorizationHeader),
                mudarSenhaDto.senhaAntiga(),
                mudarSenhaDto.novaSenha()
        );

        if (validadeDaOperacao) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/mudarSenhaPorRequest")
    public ResponseEntity mudarSenhaPorRequest(@RequestBody @Valid MudarSenhaPorRequestDto mudarSenhaPorRequestDto) {
        boolean validadeDaOperacao = usuarioService.mudarSenhaPorRequest(
                UUID.fromString(mudarSenhaPorRequestDto.idRequest()),
                mudarSenhaPorRequestDto.numeroDeRecuperacao(),
                mudarSenhaPorRequestDto.login(),
                mudarSenhaPorRequestDto.senha()
        );

        if (validadeDaOperacao) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
