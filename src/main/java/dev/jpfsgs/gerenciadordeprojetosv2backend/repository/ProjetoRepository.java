package dev.jpfsgs.gerenciadordeprojetosv2backend.repository;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
}
