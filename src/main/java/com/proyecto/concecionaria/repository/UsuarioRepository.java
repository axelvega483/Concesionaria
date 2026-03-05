package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.dni = :dni AND u.activo = true")
    Optional<Usuario> findByDniAndActivo(String dni);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
