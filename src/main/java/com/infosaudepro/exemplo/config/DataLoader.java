package com.infosaudepro.exemplo.config;

import com.infosaudepro.exemplo.model.Usuario;
import com.infosaudepro.exemplo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository repository) {
        return args -> {
            // Verifica se o banco está vazio antes de inserir (evita erro de chave duplicada)
            if (repository.count() == 0) {

                // 1. Geração Segura dos Hashes (BCrypt)
                String adminHash = passwordEncoder.encode("admin123");
                String medicoHash = passwordEncoder.encode("medico123");

                // 2. Criação do Usuário ADMIN
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(adminHash);
                admin.setRole("ADMIN");
                repository.save(admin); // ⬅️ SALVA

                // 3. Criação do Usuário MÉDICO
                Usuario medico = new Usuario();
                medico.setUsername("medico");
                medico.setPassword(medicoHash);
                medico.setRole("MEDICO");
                repository.save(medico); // ⬅️ SALVA

                System.out.println("Usuários de segurança ADMIN e MEDICO inseridos via DataLoader.");
            }
        };
    }
}