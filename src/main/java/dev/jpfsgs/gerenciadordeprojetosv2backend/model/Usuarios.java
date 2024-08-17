package dev.jpfsgs.gerenciadordeprojetosv2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_gen")
    @SequenceGenerator(name = "usuario_id_gen", sequenceName = "usuario_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "hashed_senha", nullable = false)
    private String hashedSenha;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projetos> projetos;

}