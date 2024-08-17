package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CadastrarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.UsuariosService;
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
    public List<UsuarioResponseDTO> getUsuarios() {
        return usuariosService.getAllUsuarios();
    }

    @PostMapping("/cadastrar")
    public UsuarioResponseDTO cadastrarUsuario(CadastrarUsuarioRequestDTO usuario){
        return usuariosService.registerUsuario(usuario);
    }

    @PutMapping("/editar")
    public UsuarioResponseDTO editarUsuario(AtualizarUsuarioRequestDTO usuario){
        return usuariosService.updateUsuario(usuario);
    }

    @DeleteMapping("/deletar")
    public void deletarUsuario(Integer id){
        usuariosService.deleteUsuario(id);
    }

}
