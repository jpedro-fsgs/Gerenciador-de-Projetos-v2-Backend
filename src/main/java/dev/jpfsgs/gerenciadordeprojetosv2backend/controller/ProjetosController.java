package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.config.SecurityConfig;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetosResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetosDeletadosResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.ProjetosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetosController {
    private final ProjetosService projetosService;

    public ProjetosController(ProjetosService projetosService) {
        this.projetosService = projetosService;
    }

    @GetMapping("/")
    public List<ProjetoPublicoResponseDTO> getProjetosPublicos() {
        return projetosService.getAllProjetosPublicos();
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @GetMapping("/usuario")
    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(JwtAuthenticationToken token) {
        return projetosService.getProjetosUsuario(Integer.parseInt(token.getName()));
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @PostMapping("/criar")
    public ProjetosResponseDTO registrarProjeto(@RequestBody CriarProjetoRequestDTO projeto, JwtAuthenticationToken token) {
        ProjetoUsuarioResponseDTO addedProject = projetosService.addProjeto(projeto, Integer.parseInt(token.getName()));
        return new ProjetosResponseDTO(
                projetosService.getProjetosUsuario(Integer.parseInt(token.getName())),
                projetosService.getAllProjetosPublicos(),
                addedProject,
                null
        );
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @PostMapping("/criar_varios")
    public List<ProjetoUsuarioResponseDTO> registrarMultiplosProjetos(@RequestBody List<CriarProjetoRequestDTO> projetos, JwtAuthenticationToken token) {
        return projetosService.addManyProjeto(projetos, Integer.parseInt(token.getName()));
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @PutMapping("/editar")
    public ProjetosResponseDTO alterarProjeto(@RequestBody AtualizarProjetoRequestDTO projeto, JwtAuthenticationToken token) {
        ProjetoUsuarioResponseDTO addedProject = projetosService.updateProjeto(projeto, Integer.parseInt(token.getName()));
        return new ProjetosResponseDTO(
                projetosService.getProjetosUsuario(Integer.parseInt(token.getName())),
                projetosService.getAllProjetosPublicos(),
                addedProject, null
        );
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @DeleteMapping("/deletar")
    public ResponseEntity<ProjetosResponseDTO> removerMultiplosProjeto(@RequestBody List<Integer> ids, JwtAuthenticationToken token) {
        ProjetosDeletadosResponseDTO deletedProjetos = projetosService.deleteManyProjetos(ids, Integer.parseInt(token.getName()));

        HttpStatus status = deletedProjetos.deleted().isEmpty() ?
                HttpStatus.NOT_FOUND:
                (
                        deletedProjetos.failed().isEmpty() ?
                                HttpStatus.OK :
                                HttpStatus.PARTIAL_CONTENT
                );
        return ResponseEntity.status(status).body(
                new ProjetosResponseDTO(
                        projetosService.getProjetosUsuario(Integer.parseInt(token.getName())),
                        projetosService.getAllProjetosPublicos(),
                        null, deletedProjetos
                )
        );

    }
}
