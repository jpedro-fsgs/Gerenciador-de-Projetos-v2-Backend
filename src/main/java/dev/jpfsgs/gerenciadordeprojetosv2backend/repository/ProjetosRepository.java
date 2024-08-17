package dev.jpfsgs.gerenciadordeprojetosv2backend.repository;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projetos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetosRepository extends JpaRepository<Projetos, Integer> {
}
