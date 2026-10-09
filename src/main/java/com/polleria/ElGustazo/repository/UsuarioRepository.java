package com.polleria.ElGustazo.repository;

import com.polleria.ElGustazo.model.Usuario; // O la ruta de tu entidad
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}