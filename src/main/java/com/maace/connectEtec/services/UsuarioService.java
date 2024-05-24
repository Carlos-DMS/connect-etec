package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.CadastroUsuarioDto;
import com.maace.connectEtec.models.PerfilUsuarioModel;
import com.maace.connectEtec.models.RequestValidacaoModel;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.RequestValidacaoRepository;
import com.maace.connectEtec.repositories.UsuarioRepository;
import com.maace.connectEtec.security.TokenService;
import org.springframework.beans.BeanUtils;
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
    RequestValidacaoRepository requestValidacaoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    public boolean cadastrar(CadastroUsuarioDto cadastroUsuarioDto,
                          UUID idRequest,
                          Integer codigoDeValidacao)
    {
        if (validacaoDeRequest(idRequest, codigoDeValidacao)) {
            UsuarioModel usuario = new UsuarioModel();
            PerfilUsuarioModel perfil = new PerfilUsuarioModel();

            BeanUtils.copyProperties(cadastroUsuarioDto, usuario);

            perfilUsuarioService.criarPerfilUsuario(perfil);

            usuario.setIdPerfilUsuario(perfil.getIdPerfil());

            usuario.setSenha(encoder.encode(usuario.getSenha()));
            usuarioRepository.save(usuario);

            return true;
        }
        return false;
    }

    public UUID recuperarConta(String destinatario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(destinatario);

        if (usuario != null) {
            return mandarEmailDeValidacao(destinatario);
        }
        return null;
    }

    public UUID emailCadastro(String destinatario) {
        UsuarioModel usuario = usuarioRepository.findByLogin(destinatario);

        if (usuario == null) {
            return mandarEmailDeValidacao(destinatario);
        }
        return null;
    }

    public UUID mandarEmailDeValidacao(String destinatario) {
        RequestValidacaoModel request = new RequestValidacaoModel(destinatario);
        requestValidacaoRepository.save(request);

        emailService.enviarEmailDeValidacao(destinatario, request.getCodigoDeValidacao());

        return request.getIdRequest();
    }

    public boolean mudarSenha(UsuarioModel usuario, String senhaAntiga, String novaSenha) {
        if (usuario != null && encoder.matches(senhaAntiga, usuario.getSenha())) {
            usuario.setSenha(encoder.encode(novaSenha));
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean mudarSenhaPorRequest(UUID idRequest,
                                        Integer codigoDeRecuperacao,
                                        String login, String senha)
    {
        UsuarioModel usuario = usuarioRepository.findByLogin(login);

        if (usuario != null && validacaoDeRequest(idRequest, codigoDeRecuperacao)) {

            usuario.setSenha(encoder.encode(senha));
            usuarioRepository.save(usuario);

            return true;
        }
        return false;
    }

    public boolean validacaoDeRequest(UUID idRequest, Integer codigoDeValidacao) {
        Optional<RequestValidacaoModel> request = requestValidacaoRepository.findById(idRequest);

        return request.isPresent() &&
                request.get().getMomento().plusMinutes(10).isAfter(LocalDateTime.now()) &&
                Objects.equals(request.get().getCodigoDeValidacao(), codigoDeValidacao);
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
