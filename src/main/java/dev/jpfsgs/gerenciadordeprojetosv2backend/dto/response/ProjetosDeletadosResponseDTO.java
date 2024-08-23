package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

import java.util.List;
import java.util.Map;

public record ProjetosDeletadosResponseDTO(List<Integer> deleted, Map<Integer, String> failed) {
}
