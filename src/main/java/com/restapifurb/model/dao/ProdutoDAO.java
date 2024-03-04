package com.restapifurb.model.dao;

import com.restapifurb.model.Produto;
import com.restapifurb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Long> {
    void deleteByUsuario(Usuario idUsuario);

    List<Produto> findByUsuario(Usuario usuario);

    Optional<Produto> findByIdAndUsuario(Long id, Usuario usuario);
}
