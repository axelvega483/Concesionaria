package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByDniAndActivoTrue(String dni);

    Optional<Usuario> findByEmail(String email);

}
