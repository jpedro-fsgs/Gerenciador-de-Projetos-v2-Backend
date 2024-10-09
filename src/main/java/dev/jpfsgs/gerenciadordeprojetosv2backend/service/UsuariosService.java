package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.AtualizarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.CadastrarUsuarioRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioDeletadoResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.exception.custom.UserAlreadyExistsException;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {
    private final UsuariosRepository usuariosRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository usuariosRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public List<UsuarioResponseDTO> getAllUsuarios(Integer id) {
        if(!Arrays.asList(4, 5).contains(id)){
            throw new BadCredentialsException("User has no permission");
        }
        return usuariosRepository.findAll().stream()
                .map(UsuarioResponseDTO::new).toList();
    }

    public UsuarioResponseDTO registerUsuario(CadastrarUsuarioRequestDTO usuario){
        if(usuariosRepository.findByUsername(usuario.username()).isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        Usuarios newUsuario = new Usuarios();
        newUsuario.setUsername(usuario.username().strip());
        newUsuario.setHashedSenha(passwordEncoder.encode(usuario.password()));
        newUsuario.setAtivo(true);
        return new UsuarioResponseDTO(usuariosRepository.save(newUsuario));
    }

    public UsuarioResponseDTO updateUsuario(AtualizarUsuarioRequestDTO usuario, Integer id){
        Optional<Usuarios> updateUsuarioOpt = usuariosRepository.findById(id);
        if(updateUsuarioOpt.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        Usuarios updateUsuario = updateUsuarioOpt.get();
        if(usuario.username().isPresent()){
            updateUsuario.setUsername(usuario.username().get());
        }
        if(usuario.password().isPresent()){
            updateUsuario.setHashedSenha(passwordEncoder.encode(usuario.password().get()));
        }
        if(usuario.ativo().isPresent()){
            updateUsuario.setAtivo(usuario.ativo().get());
        }
        return new UsuarioResponseDTO(usuariosRepository.save(updateUsuario));
    }

    public UsuarioDeletadoResponseDTO deleteUsuario(Integer id){
        Optional<Usuarios> deleteUsuarioOpt = usuariosRepository.findById(id);
        if(deleteUsuarioOpt.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        Usuarios deletedUsuario = deleteUsuarioOpt.get();
        usuariosRepository.delete(deletedUsuario);
        return new UsuarioDeletadoResponseDTO(true, deletedUsuario.getUsername(), deletedUsuario.getId());
    }
}
