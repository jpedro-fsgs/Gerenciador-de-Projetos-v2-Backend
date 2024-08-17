package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CadastrarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CriarProjetoRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {
    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuariosRepository.findAll().stream().map(usuario -> new UsuarioResponseDTO(usuario)).toList();
    }

    public UsuarioResponseDTO registerUsuario(CadastrarUsuarioRequestDTO usuario){
        Usuarios newUsuario = new Usuarios();
        newUsuario.setUsername(usuario.username().strip());
        newUsuario.setHashedSenha(usuario.password());
        newUsuario.setAtivo(true);
        return new UsuarioResponseDTO(usuariosRepository.save(newUsuario));
    }

    public UsuarioResponseDTO updateUsuario(AtualizarUsuarioRequestDTO usuario){
        Usuarios updateUsuario = usuariosRepository.findById(usuario.id()).get();
        if(usuario.username().isPresent()){
            updateUsuario.setUsername(usuario.username().get());
        }
        if(usuario.password().isPresent()){
            updateUsuario.setHashedSenha(usuario.password().get());
        }
        if(usuario.ativo().isPresent()){
            updateUsuario.setAtivo(usuario.ativo().get());
        }
        return new UsuarioResponseDTO(usuariosRepository.save(updateUsuario));
    }

    public void deleteUsuario(Integer id){
        usuariosRepository.deleteById(id);
    }
}
