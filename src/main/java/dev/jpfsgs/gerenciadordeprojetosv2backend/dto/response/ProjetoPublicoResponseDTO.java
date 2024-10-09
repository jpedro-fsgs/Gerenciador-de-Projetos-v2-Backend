package dev.jpfsgs.gerenciadordeprojetosv2backend.dto.response;

import dev.jpfsgs.gerenciadordeprojetosv2backend.model.Projetos;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Getter
public class ProjetoPublicoResponseDTO implements Serializable {
    private final Integer id;
    private final String nome;
    private final String descricao;
    private final String link;
    private final Instant criacao;
    private final LocalDate prazo;
    private final Boolean is_concluido;
    private final String usuario;
    private final Integer usuario_id;

    public ProjetoPublicoResponseDTO(Projetos projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.link = projeto.getLink();
        this.criacao = projeto.getCriacao();
        this.prazo = projeto.getPrazo();
        this.is_concluido = projeto.getIsConcluido();
        this.usuario = projeto.getUsuario().getUsername();
        this.usuario_id = projeto.getUsuario().getId();
    }
}
