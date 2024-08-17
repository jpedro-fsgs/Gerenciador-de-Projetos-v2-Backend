package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projetos;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ProjetoUsuarioResponseDTO {
    private final Integer id;
    private final String nome;
    private final String descricao;
    private final String link;
    private final Instant criacao;
    private final Instant prazo;
    private final Boolean is_publico;
    private final Boolean is_concluido;

    public ProjetoUsuarioResponseDTO(Projetos projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.link = projeto.getLink();
        this.criacao = projeto.getCriacao();
        this.prazo = projeto.getPrazo();
        this.is_publico = projeto.getIsPublico();
        this.is_concluido = projeto.getIsConcluido();
    }
}
