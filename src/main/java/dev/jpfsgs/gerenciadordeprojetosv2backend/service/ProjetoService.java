package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.ProjetosDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projeto;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {
    private ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public List<ProjetosDTO> getAllProjetos(){
        List<Projeto> projetos = projetoRepository.findAll();
        return projetos.stream().map(projeto -> new ProjetosDTO(projeto)).toList();
    }

    public Projeto getProjetoById(int id){
        return projetoRepository.findById(id).get();
    }
}
