package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

import java.util.List;

public record ProjetosResponseDTO(
        List<ProjetoUsuarioResponseDTO> userProjects,
        List<ProjetoPublicoResponseDTO> publicProjects,
        ProjetoUsuarioResponseDTO addedProject,
        ProjetosDeletadosResponseDTO deletedProjects
) {
}
