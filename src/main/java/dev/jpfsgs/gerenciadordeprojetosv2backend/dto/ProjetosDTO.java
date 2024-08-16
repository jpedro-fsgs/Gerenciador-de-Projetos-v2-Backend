package dev.jpfsgs.gerenciadordeprojetosv2backend.dto;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class ProjetosDTO {
    private final int id;
    private final String nome;
    private final String descricao;
    private final String link;
    private final Instant criacao;
    private final Instant prazo;
    private final Boolean is_publico;
    private final Boolean is_concluido;
    private final String usuario;

    public ProjetosDTO(Projeto projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.link = projeto.getLink();
        this.criacao = projeto.getCriacao();
        this.prazo = projeto.getPrazo();
        this.is_publico = projeto.getIsPublico();
        this.is_concluido = projeto.getIsConcluido();
        this.usuario = projeto.getUsuario().getUsername();
    }
}
