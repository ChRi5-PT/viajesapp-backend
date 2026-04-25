package com.viajesapp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String pais;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;


    private String email;
    private String password;

}
