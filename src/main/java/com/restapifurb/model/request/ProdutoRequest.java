package com.restapifurb.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoRequest(Long id,
                             @NotBlank(message = "Você precisa inserir o nome do produto.") String nome,
                             @NotNull(message = "Você precisa inserir o preço do produto.") BigDecimal preco) {
}
