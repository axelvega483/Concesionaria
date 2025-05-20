package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.activo=TRUE")
    public List<Cliente> findAllActivo();

}
