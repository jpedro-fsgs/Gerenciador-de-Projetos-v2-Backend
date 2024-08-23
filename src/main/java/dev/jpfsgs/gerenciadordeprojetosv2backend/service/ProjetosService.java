package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoPublicoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetoUsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.ProjetosDeletadosResponseDTO;
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
    private final ProjetosRepository projetosRepository;
    private final UsuariosRepository usuariosRepository;

    public ProjetosService(ProjetosRepository projetosRepository, UsuariosRepository usuariosRepository) {
        this.projetosRepository = projetosRepository;
        this.usuariosRepository = usuariosRepository;
    }

    public List<ProjetoPublicoResponseDTO> getAllProjetosPublicos() {
        List<Projetos> projetos = projetosRepository.findAll();
        if (projetos.isEmpty()) {
            throw new EntityNotFoundException("No projects found");
        }
        return projetos.stream().filter(Projetos::getIsPublico).map(ProjetoPublicoResponseDTO::new).toList();
    }

    public List<ProjetoUsuarioResponseDTO> getProjetosUsuario(Integer usuarioId) {
        Optional<Usuarios> usuariosOptional = usuariosRepository.findById(usuarioId);
        if (usuariosOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Usuarios usuario = usuariosOptional.get();
        return usuario.getProjetos().stream().map(ProjetoUsuarioResponseDTO::new).toList();
    }

    public ProjetoUsuarioResponseDTO addProjeto(CriarProjetoRequestDTO projeto, Integer usuarioId) {
        Optional<Usuarios> usuarioOptional = usuariosRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Projetos newProjeto = new Projetos();
        newProjeto.setNome(projeto.nome());
        newProjeto.setDescricao(projeto.descricao());
        newProjeto.setLink(projeto.link());
        newProjeto.setPrazo(projeto.prazo());
        newProjeto.setIsPublico(projeto.is_publico());
        newProjeto.setIsConcluido(projeto.is_concluido());
        newProjeto.setUsuario(usuarioOptional.get());
        return new ProjetoUsuarioResponseDTO(projetosRepository.save(newProjeto));
    }

    public List<ProjetoUsuarioResponseDTO> addManyProjeto(List<CriarProjetoRequestDTO> projetos, Integer usuarioId) {
        return projetos.stream()
                .map(newProjeto -> addProjeto(newProjeto, usuarioId))
                .toList();
    }

    public ProjetoUsuarioResponseDTO updateProjeto(AtualizarProjetoRequestDTO projeto, Integer usuarioId) {
        Optional<Projetos> updateProjetoFind = projetosRepository.findById(projeto.id());
        Optional<Usuarios> updateUsuarioFind = usuariosRepository.findById(usuarioId);
        if (updateProjetoFind.isEmpty()) {
            throw new EntityNotFoundException("Project not found");
        }
        if (updateUsuarioFind.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }


        Projetos updateProjeto = updateProjetoFind.get();
        if (!updateProjeto.getUsuario().getId().equals(updateUsuarioFind.get().getId())) {
            throw new BadCredentialsException("Project does not belong to this user");
        }
        projeto.nome().ifPresent(updateProjeto::setNome);
        projeto.descricao().ifPresent(updateProjeto::setDescricao);
        projeto.link().ifPresent(updateProjeto::setLink);
        projeto.prazo().ifPresent(updateProjeto::setPrazo);
        projeto.prazoNull().ifPresent(prazoNull -> {
            if (prazoNull) updateProjeto.setPrazo(null);
        });
        projeto.is_publico().ifPresent(updateProjeto::setIsPublico);

        return new ProjetoUsuarioResponseDTO(projetosRepository.save(updateProjeto));
    }

    public void deleteProjeto(Integer id, Integer usuarioId) {
        Optional<Projetos> deleteProjetoFind = projetosRepository.findById(id);
        if (deleteProjetoFind.isEmpty()) {
            throw new EntityNotFoundException("Project not found");
        }
        Projetos deleteProjeto = deleteProjetoFind.get();
        if (!deleteProjeto.getUsuario().getId().equals(usuarioId)) {
            throw new BadCredentialsException("Project does not belong to this user");
        }
        projetosRepository.delete(deleteProjeto);
    }

    public ProjetosDeletadosResponseDTO deleteManyProjetos(List<Integer> ids, Integer usuarioId) {
        List<Integer> deleted = new ArrayList<>();
        Map<Integer, String> failed = new HashMap<>();

        for (Integer id : ids) {
            try {
                deleteProjeto(id, usuarioId);
                deleted.add(id);
            } catch (EntityNotFoundException | BadCredentialsException e) {
                failed.put(id, e.getMessage());
            }
        }

        return new ProjetosDeletadosResponseDTO(deleted, failed);
    }
}
