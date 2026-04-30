package com.viajesapp.backend.controller;

import com.viajesapp.backend.dto.AuthResponse;
import com.viajesapp.backend.dto.LoginRequest;
import com.viajesapp.backend.dto.RegisterRequest;
import com.viajesapp.backend.model.Usuario;
import com.viajesapp.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return usuarioService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody RegisterRequest request) {
    return usuarioService.registrarUsuario(request);
    }

}