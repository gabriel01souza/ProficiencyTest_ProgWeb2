package com.restapifurb.model.security.auth;

import com.restapifurb.model.Usuario;
import com.restapifurb.model.dao.UsuarioDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioDAO usuarioDAO;

    public CustomUserDetailsService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     *  Custom: quando o auth...manager chamar o authenticate, ele chama esse metodo e não o padrão.
     */
    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        Usuario user = usuarioDAO.findByNomeUsuario(nomeUsuario);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getNomeUsuario())
                        .password("123456")
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }
}
