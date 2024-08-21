package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

public record LoginResponseDTO(String acessToken, Long expiresIn) {
}
