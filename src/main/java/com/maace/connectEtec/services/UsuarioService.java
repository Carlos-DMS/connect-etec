package com.maace.connectEtec.services;

import com.maace.connectEtec.models.RequestRecuperacaoSenhaModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.RequestRecuperacaoSenhaRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import com.maace.connectEtec.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RequestRecuperacaoSenhaRepository requestRecuperacaoSenhaRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    public void cadastrar(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public UUID recuperarConta(String destinatario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(destinatario);

        if (usuario != null) {
            RequestRecuperacaoSenhaModel request = new RequestRecuperacaoSenhaModel(destinatario);
            requestRecuperacaoSenhaRepository.save(request);

            emailService.enviarEmailRecuperacaoSenha(destinatario, request.getCodigoDeRecuperacao());

            return request.getIdRequest();
        }
        return null;
    }

    public boolean mudarSenha(UUID idRequest, Integer codigoDeRecuperacao, String email, String senha) {
        UsuarioModel usuario = usuarioRepository.findByLogin(email);
        Optional<RequestRecuperacaoSenhaModel> request = requestRecuperacaoSenhaRepository.findById(idRequest);

        if (usuario != null && request.isPresent() &&
                request.get().getMomento().plusMinutes(10).isAfter(LocalDateTime.now()) &&
                Objects.equals(request.get().getCodigoDeRecuperacao(), codigoDeRecuperacao)) {

            usuario.setSenha(encoder.encode(senha));
            usuarioRepository.save(usuario);

            return true;
        }
        return false;
    }

    public UsuarioModel buscarPorToken(String authorizationHeader) {
        String token = extrairToken(authorizationHeader);
        if (token != null) {
            String login = tokenService.validarToken(token);
            return usuarioRepository.findByLogin(login);
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
