package dev.jpfsgs.gerenciadordeprojetosv2backend.repository;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByUsername(String username);
}
