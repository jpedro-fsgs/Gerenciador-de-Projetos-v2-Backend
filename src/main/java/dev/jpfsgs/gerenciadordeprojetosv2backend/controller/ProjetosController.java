package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.ProjetosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/usuario")
    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(JwtAuthenticationToken token) {
        return projetosService.getProjetosUsuario(Integer.parseInt(token.getName()));
    }

    @PostMapping("/criar")
    public ProjetoUsuarioResponseDTO registrarProjeto(@RequestBody CriarProjetoRequestDTO projeto, JwtAuthenticationToken token) {
        return projetosService.addProjeto(projeto, Integer.parseInt(token.getName()));
    }

    @PostMapping("/criar_varios")
    public List<ProjetoUsuarioResponseDTO> registrarMultiplosProjetos(@RequestBody List<CriarProjetoRequestDTO> projetos, JwtAuthenticationToken token) {
        Integer id = Integer.parseInt(token.getName());
        return projetos.stream().map(projeto -> projetosService.addProjeto(projeto, id)).toList();
    }

    @PutMapping("/editar")
    public ProjetoUsuarioResponseDTO alterarProjeto(@RequestBody AtualizarProjetoRequestDTO projeto, JwtAuthenticationToken token) {
        return projetosService.updateProjeto(projeto, Integer.parseInt(token.getName()));
    }

    @DeleteMapping("/deletar")
    public void removerProjeto(@RequestBody Integer id, JwtAuthenticationToken token) {
        projetosService.deleteProjeto(id, Integer.parseInt(token.getName()));
    }

    @DeleteMapping("/deletar_varios")
    public ResponseEntity<Map<String, Map<Integer, String>>> removerMultiplosProjeto(@RequestBody List<Integer> ids, JwtAuthenticationToken token) {
        Map<String, Map<Integer, String>> deleted = projetosService.deleteManyProjetos(ids, Integer.parseInt(token.getName()));

        HttpStatus status = deleted.get("failed").isEmpty() ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT;
        return ResponseEntity.status(status).body(deleted);

    }
}
