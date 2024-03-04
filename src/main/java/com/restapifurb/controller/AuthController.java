package com.restapifurb.controller;

import com.restapifurb.model.security.auth.JwtUtil;
import com.restapifurb.model.Usuario;
import com.restapifurb.model.request.RequestUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody RequestUser user) {
        try {
            // Authenticate irá gerar a authenticação
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.user(), "123456"));
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(user.user());
            String token = jwtUtil.createToken(usuario);

            return ResponseEntity.ok("Token: " + token);
        } catch (BadCredentialsException e) {
            String messageError = "Invalid username or password";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
