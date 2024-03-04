package com.restapifurb.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;


@Builder
public record UsuarioRequest(Long idUsuario,
                             @NotBlank(message = "Você precisa informar o nome de usuário.") String nomeUsuario,
                             @NotBlank(message = "Você precisa informar o telefone.") String telefoneUsuario,
                             List<@Valid ProdutoRequest> produtos) {
}
