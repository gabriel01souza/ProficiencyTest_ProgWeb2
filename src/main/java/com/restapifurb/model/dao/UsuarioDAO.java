package com.restapifurb.model.dao;

import com.restapifurb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    Usuario findByNomeUsuario(String nome);
    boolean existsByNomeUsuario(String nome);
}
