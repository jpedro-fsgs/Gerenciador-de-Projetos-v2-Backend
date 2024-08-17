package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request;

import java.time.Instant;
import java.util.Optional;

public record AtualizarProjetoRequestDTO(
        Integer id,
        Optional<String> nome,
        Optional<String> descricao,
        Optional<String> link,
        Optional<Instant> prazo,
        Optional<Boolean> is_publico,
        Optional<Boolean> is_concluido

) {
}