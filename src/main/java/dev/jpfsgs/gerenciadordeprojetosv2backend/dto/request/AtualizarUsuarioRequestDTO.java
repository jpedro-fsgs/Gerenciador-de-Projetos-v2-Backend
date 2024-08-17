package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request;

import java.util.Optional;

public record AtualizarUsuarioRequestDTO(
        Integer id,
        Optional<String> username,
        Optional<String> password,
        Optional<Boolean> ativo
) {
}
