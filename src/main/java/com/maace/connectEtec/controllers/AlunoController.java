package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.AlunoDto;
import com.maace.connectEtec.models.AlunoModel;
import com.maace.connectEtec.services.AlunoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
public class AlunoController extends UsuarioController<AlunoModel, AlunoDto> {

    @Autowired
    public AlunoController(AlunoService service) {
        super(service);
    }

    @Override
    protected AlunoModel convertDtoToEntity(AlunoDto dto) {
        AlunoModel model = new AlunoModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
