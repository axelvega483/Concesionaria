package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Venta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("SELECT v FROM Venta v WHERE v.activo=TRUE")
    public List<Venta> findAllActivo();
    
}
