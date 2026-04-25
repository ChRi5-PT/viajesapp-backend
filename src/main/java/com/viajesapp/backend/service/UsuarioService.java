package com.viajesapp.backend.service;

import com.viajesapp.backend.model.Usuario;
import com.viajesapp.backend.repository.UsuarioRepository;
import com.viajesapp.backend.dto.AuthResponse;
import com.viajesapp.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public Usuario guardarUsuario(Usuario usuario) {


        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);

    }

    public AuthResponse login(String email, String password) {

        email = email.trim();
        password = password.trim();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(email);

        usuario.setPassword(null);

        return new AuthResponse(token, usuario);
    }
}