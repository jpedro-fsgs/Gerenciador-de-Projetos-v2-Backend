package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.ProjetosDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projeto;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.ProjetoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetosController {
    private final ProjetoService projetoService;

    public ProjetosController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping("/")
    public List<ProjetosDTO> getProjeto() {
        return projetoService.getAllProjetos();
    }
}
