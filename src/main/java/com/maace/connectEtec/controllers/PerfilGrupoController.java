package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.perfilGrupo.RespostaPerfilGrupoDto;
import com.maace.connectEtec.models.GrupoModel;
import com.maace.connectEtec.models.PerfilGrupoModel;
import com.maace.connectEtec.services.GrupoService;
import com.maace.connectEtec.services.PerfilGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/perfisGrupo")
public class PerfilGrupoController {

    @Autowired
    PerfilGrupoService perfilGrupoService;

    @Autowired
    GrupoService grupoService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPerfilGrupoDto> buscarPorId(@PathVariable UUID idPerfilGrupo, UUID idGrupo){
        Optional<PerfilGrupoModel> perfilOptional = perfilGrupoService.buscarPorId(idPerfilGrupo);
        Optional<GrupoModel> grupoOptional = grupoService.buscarPorId(idGrupo);
        if(perfilOptional.isPresent() && grupoOptional.isPresent()){
            RespostaPerfilGrupoDto perfilGrupoDto = new RespostaPerfilGrupoDto(grupoOptional.get().getNome(), perfilOptional.get().getUrlFotoPerfil());
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<RespostaPerfilGrupoDto>> listarTodos(){
        List<PerfilGrupoModel> perfisGrupoModel = perfilGrupoService.listarTodos();
        if(perfisGrupoModel != null) {
            List<RespostaPerfilGrupoDto> perfilGrupoDtos = new ArrayList<>();
            for(PerfilGrupoModel perfilGrupoModel : perfisGrupoModel){
                //perfilGrupoDtos.add(new RespostaPerfilGrupoDto(/*como que vou pegar o grupo dessa bomba aqui?*/, perfilGrupoModel.getUrlFotoPerfil());
            }
            return ResponseEntity.status(HttpStatus.OK).body(perfilGrupoDtos);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
