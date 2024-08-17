package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request;

import java.time.Instant;

public record CriarProjetoRequestDTO(
        String nome,
        String descricao,
        String link,
        Instant prazo,
        Boolean is_publico,
        Boolean is_concluido

) {
}
