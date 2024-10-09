package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CadastrarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioDeletadoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.UsuariosService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/")
    public List<UsuarioResponseDTO> getUsuarios(JwtAuthenticationToken token) {
        return usuariosService
                .getAllUsuarios(Integer.parseInt((token.getName())));
    }

    @PostMapping("/cadastrar")
    public UsuarioResponseDTO cadastrarUsuario(@RequestBody CadastrarUsuarioRequestDTO usuario){
        return usuariosService.registerUsuario(usuario);
    }

    @PutMapping("/editar")
    public UsuarioResponseDTO editarUsuario(@RequestBody AtualizarUsuarioRequestDTO usuario, JwtAuthenticationToken token){
        return usuariosService.updateUsuario(usuario, Integer.parseInt(token.getName()));
    }

    @DeleteMapping("/deletar")
    public UsuarioDeletadoResponseDTO deletarUsuario(JwtAuthenticationToken token){
        return usuariosService.deleteUsuario(Integer.parseInt(token.getName()));
    }

}
