package com.restapifurb.model.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoResponse(Long id, String nome, BigDecimal preco) {

    public static ProdutoResponse of(Long id, String nome, BigDecimal preco) {
        return ProdutoResponse.builder()
                .id(id)
                .nome(nome)
                .preco(preco)
                .build();
    }
}
