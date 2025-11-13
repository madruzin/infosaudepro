package com.infosaudepro.exemplo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Usuario")
@Getter // Gera getRole() e os setters para todos os campos
@Setter // Gera todos os setters
public class Usuario implements UserDetails {

    @Id
    private String username;
    private String password;
    private String role;

    // 1. CONSTRUTOR PADRÃO (Obrigatório para o Hibernate/JPA)
    public Usuario() {
    }

    // 2. CONSTRUTOR DE 3 ARGUMENTOS (Obrigatório para o seu DataLoader)
    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // 3. IMPLEMENTAÇÃO EXPLÍCITA PARA USERDETAILS (Resolve o erro de compilação do Lombok)
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }


    // Outros métodos da interface UserDetails
    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}