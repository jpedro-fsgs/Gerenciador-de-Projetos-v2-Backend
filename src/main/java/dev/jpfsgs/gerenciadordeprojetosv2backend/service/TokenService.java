package dev.jpfsgs.gerenciadordeprojetosv2backend.service;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.LoginRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.LoginResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Usuarios;
import dev.jpfsgs.gerenciadordeprojetosv2backend.repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {
    private final UsuariosRepository usuariosRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenService(UsuariosRepository usuariosRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Usuarios> user = usuariosRepository.findByUsername(loginRequest.username());
        if (user.isEmpty() || !bCryptPasswordEncoder.matches(loginRequest.password(), user.get().getHashedSenha())){
            throw new BadCredentialsException("Invalid username or password");
        }

        Instant now = Instant.now();
        long expiresIn = 9000L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Gerenciador de Projetos v2")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue, expiresIn);
    }

    public String userById(Integer id){
        Optional<Usuarios> usuario = usuariosRepository.findById(id);
        if(usuario.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        return usuario.get().getUsername();
    }
}
