package com.maace.connectEtec.dtos.evento;

import jakarta.validation.constraints.NotBlank;

public record EventoDTO(@NotBlank String urlMidia) {
}
