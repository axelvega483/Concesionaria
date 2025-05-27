package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioMapper;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPutDTO;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.service.UsuarioService;
import com.proyecto.concecionaria.service.VentaService;
import com.proyecto.concecionaria.util.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listarUsuario() {
        try {
            List<UsuarioGetDTO> dto = usuarioService.listar()
                    .stream()
                    .map(UsuarioMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Listado de usuarios obtenidos correctamente", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: ", null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.obtener(id).orElse(null);
            if (usuario != null) {
                UsuarioGetDTO dto = UsuarioMapper.toDTO(usuario);
                return new ResponseEntity<>(new ApiResponse<>("Usuario encontrado con éxito", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: ", null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioPostDTO usuarioDTO) {
        try {
            if (usuarioService.existe(usuarioDTO.getDni())) {
                return new ResponseEntity<>(new ApiResponse<>("El Usuario ya existe", usuarioDTO, false), HttpStatus.CONFLICT);
            }
            List<Venta> ventas = ventaService.obtenerById(usuarioDTO.getVentasId());
            if (ventas.size() != usuarioDTO.getVentasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Una o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            Usuario usuario = new Usuario();
            usuario.setActivo(Boolean.TRUE);
            usuario.setDni(usuarioDTO.getDni());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setRol(usuarioDTO.getRol());
            usuario.setVentas(ventas);
            UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(usuario));
            return new ResponseEntity<>(new ApiResponse<>("Usuario creado ", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: ", null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioPutDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.obtener(id).orElse(null);
            if (usuario != null) {
                List<Venta> ventas = ventaService.obtenerById(usuarioDTO.getVentasId());
                if (ventas.size() != usuarioDTO.getVentasId().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Una o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                usuario.setActivo(Boolean.TRUE);
                usuario.setDni(usuarioDTO.getDni());
                usuario.setEmail(usuarioDTO.getEmail());
                usuario.setNombre(usuarioDTO.getNombre());
                usuario.setPassword(usuarioDTO.getPassword());
                usuario.setRol(usuarioDTO.getRol());
                usuario.setVentas(ventas);
                UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(usuario));
                return new ResponseEntity<>(new ApiResponse<>("Usuario actualizado ", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro usuario", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id
    ) {
        try {
            Usuario usuario = usuarioService.obtener(id).orElse(null);
            if (usuario != null) {
                usuarioService.eliminar(id);
                UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(usuario));
                return new ResponseEntity<>(new ApiResponse<>("Usuario inactivo", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro usuario", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
