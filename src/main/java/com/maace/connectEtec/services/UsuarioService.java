package com.maace.connectEtec.services;

import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import com.maace.connectEtec.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public void cadastrar(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public Integer recuperarConta(String destinatario) {
        Random rand = new Random();

        UsuarioModel usuario = repository.findByLogin(destinatario);

        if (usuario != null) {
            int numero = rand.nextInt(900000) + 100000;
            emailService.enviarEmailRecuperacaoSenha(destinatario, numero);
            return numero;
        }
        return null;
    }

    public void mudarSenha(String email, String senha) {
        UsuarioModel usuario = repository.findByLogin(email);

        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public UsuarioModel buscarPorToken(String authorizationHeader) {
        String token = extrairToken(authorizationHeader);
        if (token != null) {
            String login = tokenService.validarToken(token);
            return repository.findByLogin(login);
        } else {
            return null;
        }
    }

    private String extrairToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
