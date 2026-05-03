package com.viajesapp.backend.service;

import com.viajesapp.backend.model.Usuario;
import com.viajesapp.backend.repository.UsuarioRepository;
import com.viajesapp.backend.dto.AuthResponse;
import com.viajesapp.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.viajesapp.backend.dto.RegisterRequest;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public void borrarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado para eliminar");
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario modificarUsuario(Long id, Usuario datosNuevos) {
        Usuario usuario = buscarPorId(id);
        usuario.setNombres(datosNuevos.getNombres());
        usuario.setApellidos(datosNuevos.getApellidos());
        usuario.setPais(datosNuevos.getPais());
        return usuarioRepository.save(usuario);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public AuthResponse login(String email, String password) {
        // 1. Buscamos al usuario (Si no existe -> 404 Not Found)
        Usuario usuario = usuarioRepository.findByEmail(email.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // 2. Verificamos contraseña (Si falla -> 401 Unauthorized)
        if (!passwordEncoder.matches(password.trim(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        // 3. Generamos el JWT
        String token = jwtService.generateToken(email);

        // 4. Clonamos al usuario para la respuesta y evitamos mutar la entidad de la base de datos
        Usuario usuarioRespuesta = new Usuario();
        usuarioRespuesta.setId(usuario.getId());
        usuarioRespuesta.setNombres(usuario.getNombres());
        usuarioRespuesta.setApellidos(usuario.getApellidos());
        usuarioRespuesta.setEmail(usuario.getEmail());
        usuarioRespuesta.setPais(usuario.getPais());
        usuarioRespuesta.setTipoDocumento(usuario.getTipoDocumento());
        usuarioRespuesta.setNumeroDocumento(usuario.getNumeroDocumento());
        // NO seteamos el password en el clon

        return new AuthResponse(token, usuarioRespuesta);
    }

    public Usuario registrarUsuario(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail().trim()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(request.getNombres().trim());
        nuevoUsuario.setApellidos(request.getApellidos().trim());
        nuevoUsuario.setEmail(request.getEmail().trim());
        nuevoUsuario.setPais(request.getPais().trim());
        nuevoUsuario.setTipoDocumento(request.getTipoDocumento());
        nuevoUsuario.setNumeroDocumento(request.getNumeroDocumento());

        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));

        return usuarioRepository.save(nuevoUsuario);
    }
}