package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPutDTO;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz usuarioService;

    @Operation(summary = "Login de usuario", description = "Autentica un usuario mediante correo y contraseña")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login exitoso"), @ApiResponse(responseCode = "401", description = "Credenciales inválidas"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter(description = "Credenciales de acceso del usuario", required = true) @Valid  @RequestBody UsuarioPostDTO postDTO) {
        Optional<UsuarioGetDTO> dto = usuarioService.findByCorreoAndPassword(postDTO.getEmail(), postDTO.getPassword());
        return new ResponseEntity<>(new CustomApiResponse<>("Login correcto", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<?> listarUsuario() {
        List<UsuarioGetDTO> dto = usuarioService.listar();
        return new ResponseEntity<>(new CustomApiResponse<>("Listado de usuarios obtenidos correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca y devuelve un usuario específico basado en su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerUsuario(@Parameter(description = "ID del usuario a buscar", example = "1", required = true) @PathVariable Integer id) {
        UsuarioGetDTO dto = usuarioService.obtener(id).orElse(null);
        return new ResponseEntity<>(new CustomApiResponse<>("Usuario encontrado con éxito", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "409", description = "El usuario ya existe"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping("")
    public ResponseEntity<?> crearUsuario(@Parameter(description = "Datos del nuevo usuario a registrar", required = true) @Valid @RequestBody UsuarioPostDTO usuarioDTO) {
        UsuarioGetDTO dto = usuarioService.crear(usuarioDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Usuario creado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarUsuario(@Parameter(description = "ID del usuario a actualizar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados del usuario", required = true) @RequestBody UsuarioPutDTO usuarioDTO) {
        UsuarioGetDTO dto = usuarioService.actualizar(id, usuarioDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Usuario actualizado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar/Desactivar usuario", description = "Elimina o desactiva un usuario del sistema (cambia su estado a inactivo)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@Parameter(description = "ID del usuario a eliminar/desactivar", example = "1", required = true) @PathVariable Integer id) {
        UsuarioGetDTO dto = usuarioService.eliminar(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Usuario inactivo", dto, true), HttpStatus.OK);
    }
}
