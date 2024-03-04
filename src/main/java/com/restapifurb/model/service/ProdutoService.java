package com.restapifurb.model.service;

import com.restapifurb.exception.ExceptionNotFound;
import com.restapifurb.model.Produto;
import com.restapifurb.model.Usuario;
import com.restapifurb.model.dao.ProdutoDAO;
import com.restapifurb.model.request.ProdutoRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {

    @Autowired
    @Lazy
    private ProdutoDAO produtoDAO;

    public List<Produto> getProdutos(Usuario usuario) {
        return produtoDAO.findByUsuario(usuario);
    }

    @Transactional
    public void updateProdutos(Usuario usuario , List<Produto> produtos) {
        produtos.forEach(produto -> produto.setUsuario(usuario));
        produtoDAO.saveAll(produtos);
    }

    @Transactional
    public void deleteProdutosByIdDespesa(Usuario usuario) {
        produtoDAO.deleteByUsuario(usuario);
    }

    public List<Produto> atualizarProdutos(Usuario usuario, List<ProdutoRequest> produtos) {
        if (CollectionUtils.isEmpty(produtos)) return java.util.Collections.emptyList();
        var entities = produtos.stream().map(request -> {
            var entity = produtoDAO.findByIdAndUsuario(request.id(), usuario)
                    .orElseThrow(() -> new ExceptionNotFound("O produto de id " + request.id() + ", não foi encontrado!"));
            setValue(entity, request);
            entity.setUsuario(usuario);
            return entity;
        }).toList();
        return produtoDAO.saveAll(entities);
    }

    private static void setValue(Produto entity, ProdutoRequest request) {
        if (Objects.nonNull(request.nome())) entity.setNome(request.nome());
        if (Objects.nonNull(request.preco())) entity.setPreco(request.preco());
    }
}
