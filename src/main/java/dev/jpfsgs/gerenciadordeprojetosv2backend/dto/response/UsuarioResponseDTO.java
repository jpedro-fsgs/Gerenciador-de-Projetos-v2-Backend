package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import lombok.Getter;

@Getter
public class UsuarioResponseDTO {
    private final Integer id;
    private final String username;

    public UsuarioResponseDTO(Usuarios usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
    }
}
