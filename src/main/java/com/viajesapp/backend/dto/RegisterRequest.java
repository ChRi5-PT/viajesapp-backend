package com.viajesapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String pais;
    private String tipoDocumento;
    private String numeroDocumento;
}