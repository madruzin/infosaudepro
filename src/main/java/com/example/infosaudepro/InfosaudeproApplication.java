package com.example.infosaudepro; // O pacote da sua classe principal

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan; // ⬅️ NOVA IMPORTAÇÃO

@SpringBootApplication
@ComponentScan(basePackages = "com.infosaudepro.exemplo") // Continua útil
@EnableJpaRepositories(basePackages = "com.infosaudepro.exemplo.repository")
@EntityScan(basePackages = "com.infosaudepro.exemplo.model") // ⬅️ ADICIONE ISSO AQUI!
public class InfosaudeproApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfosaudeproApplication.class, args);
    }
}