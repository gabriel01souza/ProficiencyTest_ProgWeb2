package com.restapifurb.controller;

import com.restapifurb.exception.ExceptionNotFound;
import com.restapifurb.model.request.UsuarioRequest;
import com.restapifurb.model.response.Message;
import com.restapifurb.model.response.ResponseMessage;
import com.restapifurb.model.response.UsuarioResponse;
import com.restapifurb.model.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/RestAPIFurb")
@Tag(name = "Comandas")
@SecurityRequirement(name = "bearer-key")
public class FurbController {

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @GetMapping(value = "/comandas")
    @Operation(summary = "Buscar comandas", description = "Buscar todas as comandas",
            tags = {"Comandas"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<UsuarioResponse> getComandas() {
        return usuarioService.getComandas();
    }

    @GetMapping(path = "/comandas/{id}")
    @Operation(summary = "Buscar comanda por Id", description = "Busca comanda por Id",
            tags = {"Comandas"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public UsuarioResponse getComanda(@PathVariable(name = "id") Long id) {
        return usuarioService.getComanda(id);
    }

    @PostMapping(path = "/comandas")
    @Operation(summary = "Inserir comanda", description = "Inserir uma comanda",
            tags = {"Comandas"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public UsuarioResponse inserirComanda(@Valid @RequestBody UsuarioRequest usuario) {
        return usuarioService.inserirComanda(usuario);
    }

    @PutMapping(path = "/comandas/{id}")
    @Operation(summary = "Atualizar comanda", description = "Atualizar comanda",
            tags = {"Comandas"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public UsuarioResponse atualizarComanda(@PathVariable(name = "id") Long id, @RequestBody UsuarioRequest usuarioRequest) {
         return usuarioService.atualizarComanda(id, usuarioRequest);
    }

    @DeleteMapping(value = "/comandas/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Deletar uma comanda", description = "Deletar uma comanda",
            tags = {"Comandas"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<?> deleteUsuario(@PathVariable(name = "id") Long id) throws Exception {
        try {
            usuarioService.deleteUsuarioById(id);
            return ResponseEntity.ok(new ResponseMessage(new Message("comanda removida")));
        } catch (ExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir a comanda " + id);
        }
    }
}
