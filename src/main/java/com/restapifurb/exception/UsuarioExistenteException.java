package com.restapifurb.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioExistenteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioExistenteException(String ex) {
        super(ex);
    }
}