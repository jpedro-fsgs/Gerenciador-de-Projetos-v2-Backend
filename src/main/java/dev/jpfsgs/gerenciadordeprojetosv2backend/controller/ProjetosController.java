package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.ProjetosService;
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

    @GetMapping("/usuario")
    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(@RequestParam Integer id) {
        return projetosService.getProjetosUsuario(id);
    }

    @PostMapping("/criar")
    public ProjetoUsuarioResponseDTO registrarProjeto(@RequestBody CriarProjetoRequestDTO projeto, @RequestParam Integer id) {
        return projetosService.addProjeto(projeto, id);
    }

    @PostMapping("/criar_varios")
    public List<ProjetoUsuarioResponseDTO> registrarMultiplosProjetos(@RequestBody List<CriarProjetoRequestDTO> projetos, @RequestParam Integer id) {
        return projetos.stream().map(projeto -> projetosService.addProjeto(projeto, id)).toList();
    }

    @PutMapping("/editar")
    public ProjetoUsuarioResponseDTO alterarProjeto(@RequestBody AtualizarProjetoRequestDTO projeto) {
        return projetosService.updateProjeto(projeto);
    }

    @DeleteMapping("/deletar")
    public void removerProjeto(@RequestParam Integer id) {
        projetosService.deleteProjeto(id);
    }

    @DeleteMapping("/deletar_varios")
    public void removerMultiplosProjeto(@RequestParam List<Integer> ids) {
        projetosService.deleteManyProjetos(ids);
    }
}
