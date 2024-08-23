package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

public record LoginResponseDTO(String accessToken, Long expiresIn) {
}
