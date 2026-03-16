package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean findByDniAndActivoTrue(String dni);
}
