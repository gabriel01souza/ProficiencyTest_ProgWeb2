package com.restapifurb.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;


@Builder
public record UsuarioResponse(Long idUsuario,
                              String nomeUsuario,
                              String telefoneUsuario,
                              @JsonInclude(Include.NON_EMPTY) List<ProdutoResponse> produtos) {

    /**
     *  Caso a aplicação fosse maior, eu passaria aqui apenas os dados necessário e não a entidade.
     *
     *  Vamos manter a quantidade de parametros, a camada que vai ser enviada para o usuario não
     *  terá contato com a entidade, só com os dados necessários!
     */
    public static UsuarioResponse of(Long id, String nomeUsuario, String numero, List<ProdutoResponse> produtos) {
        return UsuarioResponse.builder()
                .idUsuario(id)
                .nomeUsuario(nomeUsuario)
                .telefoneUsuario(numero)
                .produtos(produtos)
                .build();
    }
}
