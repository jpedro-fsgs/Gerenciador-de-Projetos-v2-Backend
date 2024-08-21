package dev.jpfsgs.gerenciadordeprojetosv2backend.controller;

import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.request.LoginRequestDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response.LoginResponseDTO;
import dev.jpfsgs.gerenciadordeprojetosv2backend.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
