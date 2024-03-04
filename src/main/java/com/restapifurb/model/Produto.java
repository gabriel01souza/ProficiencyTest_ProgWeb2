package com.restapifurb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restapifurb.model.request.ProdutoRequest;
import com.restapifurb.model.response.ProdutoResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"usuario"})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    public Produto (ProdutoRequest dto) {
        this.id = dto.id();
        this.nome = dto.nome();
        this.preco = dto.preco();
    }

    public Produto (ProdutoRequest dto, Usuario usuario) {
        this.id = dto.id();
        this.nome = dto.nome();
        this.preco = dto.preco();
        this.usuario = usuario;
    }
}
