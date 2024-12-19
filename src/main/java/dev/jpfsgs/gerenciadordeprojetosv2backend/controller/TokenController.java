package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.config.SecurityConfig;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.LoginRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.LoginResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.UsuarioAtualResponse;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(tokenService.login(loginRequest));
    }

    @SecurityRequirement(name = SecurityConfig.SECURITY)
    @GetMapping("/usuario")
    public UsuarioAtualResponse getCurrentUsuario(JwtAuthenticationToken token){
        Integer id = Integer.parseInt(token.getName());
        String username = tokenService.userById(id);
        return new UsuarioAtualResponse(id, username);
    }
}
