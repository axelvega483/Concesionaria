package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Pagos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {

    @Query("SELECT p FROM Pagos p WHERE p.activo=TRUE")
    public List<Pagos> findAllActivo();

}
