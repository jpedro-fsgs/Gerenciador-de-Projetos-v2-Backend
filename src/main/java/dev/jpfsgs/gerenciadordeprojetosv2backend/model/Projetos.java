package dev.jpfsgs.gerenciadordeprojetosv2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "projeto")
public class Projetos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projeto_id_gen")
    @SequenceGenerator(name = "projeto_id_gen", sequenceName = "projeto_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;

    @NotNull
    @Column(name = "criacao", nullable = false)
    @CreationTimestamp
    private Instant criacao;

    @Column(name = "prazo")
    private Instant prazo;

    @NotNull
    @Column(name = "is_publico", nullable = false)
    private Boolean isPublico = false;

    @NotNull
    @Column(name = "is_concluido", nullable = false)
    private Boolean isConcluido = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

}