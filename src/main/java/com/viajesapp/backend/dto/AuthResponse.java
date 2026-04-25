package com.viajesapp.backend.dto;

import com.viajesapp.backend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Usuario usuario;
}