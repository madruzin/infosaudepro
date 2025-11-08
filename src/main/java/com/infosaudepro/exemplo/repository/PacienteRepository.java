package com.infosaudepro.exemplo.repository;

import com.infosaudepro.exemplo.model.Paciente; // ⬅️ GARANTA QUE ESTA LINHA EXISTE
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}