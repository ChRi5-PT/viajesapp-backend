package com.viajesapp.backend.controller;

import com.viajesapp.backend.model.Usuario;
import com.viajesapp.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.modificarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.borrarUsuario(id);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
    Map<String, String> status = new HashMap<>();
    status.put("status", "up");
    status.put("service", "ms-usuarios");
    return ResponseEntity.ok(status);
}

}