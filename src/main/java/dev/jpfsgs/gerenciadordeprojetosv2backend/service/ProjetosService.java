package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projetos;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.ProjetosRepository;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetosService {
    private ProjetosRepository projetosRepository;
    private UsuariosRepository usuariosRepository;

    public ProjetosService(ProjetosRepository projetosRepository, UsuariosRepository usuariosRepository) {
        this.projetosRepository = projetosRepository;
        this.usuariosRepository = usuariosRepository;
    }

    public List<ProjetoPublicoResponseDTO> getAllProjetosPublicos(){
        List<Projetos> projetos = projetosRepository.findAll();
        return projetos.stream().filter(projeto -> projeto.getIsPublico()).map(projeto -> new ProjetoPublicoResponseDTO(projeto)).toList();
    }

    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(Integer id){
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(id);
        if(usuarioOpt.isPresent()){
            Usuarios usuario = usuarioOpt.get();
            return usuario.getProjetos().stream().map(projeto -> new ProjetoUsuarioResponseDTO(projeto)).toList();
        } else {
            return Collections.emptyList();
        }
    }

    public ProjetoUsuarioResponseDTO addProjeto(CriarProjetoRequestDTO projeto, Integer id){
        Projetos newProjeto = new Projetos();
        newProjeto.setNome(projeto.nome());
        newProjeto.setDescricao(projeto.descricao());
        newProjeto.setLink(projeto.link());
        newProjeto.setPrazo(projeto.prazo());
        newProjeto.setCriacao(Instant.now());
        newProjeto.setIsPublico(projeto.is_publico());
        newProjeto.setIsConcluido(projeto.is_concluido());
        newProjeto.setUsuario(usuariosRepository.findById(id).get());
        return new ProjetoUsuarioResponseDTO(projetosRepository.save(newProjeto));
    }

    public ProjetoUsuarioResponseDTO updateProjeto(AtualizarProjetoRequestDTO projeto){
        Projetos updateProjeto = projetosRepository.findById(projeto.id()).get();
        if(projeto.nome().isPresent()){
            updateProjeto.setNome(projeto.nome().get());
        }
        if(projeto.descricao().isPresent()){
            updateProjeto.setDescricao(projeto.descricao().get());
        }
        if(projeto.link().isPresent()){
            updateProjeto.setLink(projeto.link().get());
        }
        if(projeto.prazo().isPresent()){
            updateProjeto.setPrazo(projeto.prazo().get());
        }
        if(projeto.is_publico().isPresent()){
            updateProjeto.setIsPublico(projeto.is_publico().get());
        }
        return new ProjetoUsuarioResponseDTO(projetosRepository.save(updateProjeto));
    }

    public void deleteProjeto(Integer id){
        projetosRepository.deleteById(id);
    }

    public void deleteManyProjetos(List<Integer> ids){
        projetosRepository.deleteAllById(ids);
    }
}
