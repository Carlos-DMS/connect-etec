package com.maace.connectEtec.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data //métodos getters, setters, equals, hashCode e toString para todos os campos da classe.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_usuario")
public class UsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUsuario;
    @Column(unique = true)
    private String login;
    private String senha;
    private String nomeCompleto;
    private String nomeSocial;
}
