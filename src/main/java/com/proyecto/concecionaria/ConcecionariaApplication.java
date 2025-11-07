package com.proyecto.concecionaria;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.util.RolEmpleado;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConcecionariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcecionariaApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioInterfaz usuarioService
    ) {
        return args -> {
            List<UsuarioGetDTO> usuarios = usuarioService.listar();
            if (usuarios.isEmpty()) {
                Usuario usuarioADMIN = new Usuario();
                usuarioADMIN.setRol(RolEmpleado.ADMIN);
                usuarioADMIN.setNombre("ADMIN");
                usuarioADMIN.setPassword("admin");
                usuarioADMIN.setEmail("admin@admin.com");
                usuarioADMIN.setDni("12345678");
                usuarioADMIN.setActivo(true);
                usuarioService.guardar(usuarioADMIN);
                System.out.println("Usuario administrador inicializado con éxito.");
            }
        };
    }
}
