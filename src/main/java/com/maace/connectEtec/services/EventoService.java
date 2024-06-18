package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.evento.EventoDTO;
import com.maace.connectEtec.models.EnumTipoUsuario;
import com.maace.connectEtec.models.EventoModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    EventoRepository eventoRepository;

    public boolean criar (UsuarioModel usuario, String urlMidia) {
        if (usuario.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR)) {
            EventoModel evento = new EventoModel();
            evento.setUrlMidia(urlMidia);

            eventoRepository.save(evento);

            return true;
        }
        return false;
    }

    public List<EventoDTO> listarEventos() {
        List<EventoDTO> eventosDTO = new ArrayList<>();

        List<EventoModel> eventosModel = eventoRepository.findAll();

        for (EventoModel evento : eventosModel) {
            if (evento.getMomento().plusWeeks(1).isAfter(LocalDateTime.now())) {
                eventosDTO.add(new EventoDTO(evento.getUrlMidia()));
            }
        }

        return eventosDTO;
    }
}
