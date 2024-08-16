package dev.jpfsgs.gerenciadordeprojetosv2backend.repository;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
