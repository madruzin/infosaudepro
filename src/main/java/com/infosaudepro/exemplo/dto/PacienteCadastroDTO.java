package com.infosaudepro.exemplo.dto;
import lombok.Data;

@Data
public class PacienteCadastroDTO {
    private String nome;
    private String cpf; // Em texto puro antes de ser criptografado
    private String diagnostico; // Em texto puro antes de ser criptografado
}