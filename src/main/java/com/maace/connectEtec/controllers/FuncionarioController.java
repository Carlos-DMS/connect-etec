package com.maace.connectEtec.controllers;

import com.maace.connectEtec.dtos.FuncionarioDto;
import com.maace.connectEtec.models.FuncionarioModel;
import com.maace.connectEtec.services.FuncionarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController extends UsuarioController<FuncionarioModel, FuncionarioDto> {

    @Autowired
    public FuncionarioController(FuncionarioService service) {
        super(service);
    }

    @Override
    protected FuncionarioModel convertDtoToEntity(FuncionarioDto dto) {
        FuncionarioModel model = new FuncionarioModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
