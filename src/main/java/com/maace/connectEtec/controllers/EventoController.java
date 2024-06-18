package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.evento.EventoDTO;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.services.EventoService;
import com.maace.connectEtec.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criarEvento (
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid EventoDTO eventoDTO)
    {
        UsuarioModel usuario = usuarioService.buscarPorToken(authorizationHeader);

        boolean resposta = eventoService.criar(usuario, eventoDTO.urlMidia());

        if (resposta) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos () {
        List<EventoDTO> eventos = eventoService.listarEventos();

        if (!eventos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(eventos);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
