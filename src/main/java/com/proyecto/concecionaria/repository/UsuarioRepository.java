package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.activo=TRUE")
    public List<Usuario> findAllActivo();
}
