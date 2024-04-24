package com.maace.connectEtec.services;

import com.maace.connectEtec.dtos.RespostaUsuarioDto;
import com.maace.connectEtec.models.UsuarioModel;
import com.maace.connectEtec.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public void cadastrar(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<RespostaUsuarioDto> listarTodos(){
        List<UsuarioModel> usuarios = repository.findAll();
        List<RespostaUsuarioDto> usuariosDto = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            RespostaUsuarioDto respostaUsuarioDto = new RespostaUsuarioDto(
                    usuario.getLogin(),
                    usuario.getNomeCompleto(),
                    usuario.getNomeSocial(),
                    usuario.getTipoUsuario().getRole()
            );
            usuariosDto.add(respostaUsuarioDto);
        }
        return usuariosDto;
    }

}
