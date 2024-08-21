package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projetos;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.ProjetosRepository;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(Integer usuarioId){
        Usuarios usuario = usuariosRepository.findById(usuarioId).get();
        return usuario.getProjetos().stream().map(projeto -> new ProjetoUsuarioResponseDTO(projeto)).toList();
    }

    public ProjetoUsuarioResponseDTO addProjeto(CriarProjetoRequestDTO projeto, Integer usuarioId){
        Projetos newProjeto = new Projetos();
        newProjeto.setNome(projeto.nome());
        newProjeto.setDescricao(projeto.descricao());
        newProjeto.setLink(projeto.link());
        newProjeto.setPrazo(projeto.prazo());
        newProjeto.setIsPublico(projeto.is_publico());
        newProjeto.setIsConcluido(projeto.is_concluido());
        newProjeto.setUsuario(usuariosRepository.findById(usuarioId).get());
        return new ProjetoUsuarioResponseDTO(projetosRepository.save(newProjeto));
    }

    public ProjetoUsuarioResponseDTO updateProjeto(AtualizarProjetoRequestDTO projeto, Integer usuarioId){
        Optional<Projetos> updateProjetoFind = projetosRepository.findById(projeto.id());
        if(updateProjetoFind.isEmpty()){
            throw new EntityNotFoundException("Project not found");
        }
        Projetos updateProjeto = updateProjetoFind.get();
        if(!updateProjeto.getUsuario().getId().equals(usuariosRepository.findById(usuarioId).get().getId())){
            throw new BadCredentialsException("Project does not belong to this user");
        }
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

    public Integer deleteProjeto(Integer id, Integer usuarioId){
        Optional<Projetos> deleteProjetoFind = projetosRepository.findById(id);
        if(deleteProjetoFind.isEmpty()){
            throw new EntityNotFoundException("Project not found");
        }
        Projetos deleteProjeto = deleteProjetoFind.get();
        if(!deleteProjeto.getUsuario().getId().equals(usuarioId)){
            throw new BadCredentialsException("Project does not belong to this user");
        }
        projetosRepository.delete(deleteProjeto);
        return id;
    }

    public Map<String, Map<Integer, String>> deleteManyProjetos(List<Integer> ids, Integer usuarioId){
        Map<Integer, String> deleted = new HashMap<>();
        Map<Integer, String> failed = new HashMap<>();
        for(Integer id : ids){
            try{
                deleteProjeto(id, usuarioId);
                deleted.put(id, "success");
            } catch(EntityNotFoundException | BadCredentialsException e) {
                failed.put(id, e.getMessage());
            }
        }
        Map<String, Map<Integer, String>> result = new HashMap<>();
        result.put("deleted", deleted);
        result.put("failed", failed);

        return result;
    }
}
