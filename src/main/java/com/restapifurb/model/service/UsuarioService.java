package com.restapifurb.model.service;

import com.restapifurb.exception.ExceptionNotFound;
import com.restapifurb.exception.UsuarioExistenteException;
import com.restapifurb.model.dao.UsuarioDAO;
import com.restapifurb.model.request.UsuarioRequest;
import com.restapifurb.model.response.ProdutoResponse;
import com.restapifurb.model.response.UsuarioResponse;
import com.restapifurb.model.Produto;
import com.restapifurb.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    @Lazy
    private UsuarioDAO usuarioDAO;

    @Autowired
    @Lazy
    private ProdutoService produtoService;

    public List<UsuarioResponse> getComandas() {
        List<Usuario> usuariosEntity = usuarioDAO.findAll();
        List<UsuarioResponse> usuariosResponse = new ArrayList<>(0);

        usuariosEntity.forEach(u -> usuariosResponse.add(this.createUsuarioResponse(u, false)));
        return usuariosResponse;
    }

    public UsuarioResponse getComanda(Long idUsuario) {
        Usuario usuario = usuarioDAO.findById(idUsuario)
                .orElseThrow(() -> new ExceptionNotFound("Usuario not found."));

        return this.createUsuarioResponse(usuario, true);
    }

    public UsuarioResponse inserirComanda(UsuarioRequest usuario) {
        if (isExisteUsuarioComMesmoNome(usuario.nomeUsuario()))
            throw new UsuarioExistenteException("Já existe uma comanda com este nome de usuário");
        var produtos = usuario.produtos().stream().map(Produto::new).toList();
        Usuario entity = new Usuario(usuario, produtos);
        entity = salvarUsuario(entity);

        produtoService.updateProdutos(entity, produtos);
        return this.createUsuarioResponse(entity, true);
    }

    private boolean isExisteUsuarioComMesmoNome(String nomeUsuario) {
        return usuarioDAO.existsByNomeUsuario(nomeUsuario);
    }

    /**
     *  O id do produto precisa ser enviado (Postman/Frontend).
     * @return -> Irá retornar as informações do usuário e os produtos que foram alterados,
     *  para atualizar as infos no frontend(caso tivesse) apenas o necessário.
     */
    @Transactional
    public UsuarioResponse atualizarComanda(Long idUsuario, UsuarioRequest usuario) {
        Usuario entity = usuarioDAO.findById(idUsuario)
                .orElseThrow(() -> new ExceptionNotFound("Usuário not found."));

        List<Produto> produtos = produtoService.atualizarProdutos(entity, usuario.produtos());
        setValues(usuario, entity);
        entity = salvarUsuario(entity);

        entity.setProdutos(produtos);
        return this.createUsuarioResponse(entity, true);
    }

    private Usuario salvarUsuario(Usuario entity) {
        return usuarioDAO.save(entity);
    }

    private static void setValues(UsuarioRequest request, Usuario entity) {
        if (Objects.nonNull(request.nomeUsuario())) entity.setNomeUsuario(request.nomeUsuario());
        if (Objects.nonNull(request.telefoneUsuario())) entity.setTelefoneUsuario(request.telefoneUsuario());
        if (Objects.nonNull(request.produtos())) entity.setProdutos(request.produtos().stream()
                .map(p -> new Produto(p, entity)).collect(Collectors.toList()));
    }

    public void deleteUsuarioById(Long id) {
        var usuario = usuarioDAO.findById(id)
                .orElseThrow(() -> new ExceptionNotFound("Usuário not found."));

        produtoService.deleteProdutosByIdDespesa(usuario);
        usuarioDAO.deleteById(id);
    }

    private UsuarioResponse createUsuarioResponse(Usuario entity, boolean withProducts) {
        return UsuarioResponse.of(entity.getIdUsuario(),
                entity.getNomeUsuario(),
                entity.getTelefoneUsuario(),
                withProducts
                    ? entity.getProdutos().stream()
                        .map(p -> ProdutoResponse.of(p.getId(), p.getNome(), p.getPreco()))
                        .collect(Collectors.toList())
                    : Collections.emptyList());
    }
}