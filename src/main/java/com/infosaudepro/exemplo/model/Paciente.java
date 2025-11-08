package com.infosaudepro.exemplo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    // 🔒 Criptografados (Dados em Repouso)
    private String cpfCriptografado;
    private String diagnosticoCriptografado;
}