package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Compra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    @Query("SELECT c FROM Compra WHERE c.activo=TRUE")
    public List<Compra> findAllActivo();
}
