package com.restapifurb.model;

import com.restapifurb.model.request.UsuarioRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(length = 14)
    private String telefoneUsuario;

    @OneToMany(mappedBy = "usuario")
    private List<Produto> produtos;

    public Usuario(UsuarioRequest dto, List<Produto> produtos) {
        this.idUsuario = dto.idUsuario();
        this.nomeUsuario = dto.nomeUsuario();
        this.telefoneUsuario = dto.telefoneUsuario();
        this.produtos = produtos;
    }

    public void setIdUsuario(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("O nome do usuário precisa ser informado!");
        }
        this.idUsuario = id;
    }

    public void setNomeUsuario(String nome) {
        if (Objects.isNull(nome)) {
            throw new IllegalArgumentException("O nome do usuário precisa ser informado!");
        }
        this.nomeUsuario = nome;
    }

    public void setTelefoneUsuario(String telefone) {
        if (Objects.isNull(telefone)) {
            throw new IllegalArgumentException("O telefone do usuário precisa ser informado!");
        }
        this.telefoneUsuario = telefone;
    }

    public void setProdutos(List<Produto> produtos) {
        if (Objects.isNull(produtos)) {
            throw new IllegalArgumentException("Os produtos precisas ser informado!");
        }
        this.produtos = produtos;
    }
}
