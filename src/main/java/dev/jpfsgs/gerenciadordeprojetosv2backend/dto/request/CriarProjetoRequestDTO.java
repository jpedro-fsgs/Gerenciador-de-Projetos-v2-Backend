package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request;

import java.time.LocalDate;

public record CriarProjetoRequestDTO(
        String nome,
        String descricao,
        String link,
        LocalDate prazo,
        Boolean is_publico,
        Boolean is_concluido

) {
}
