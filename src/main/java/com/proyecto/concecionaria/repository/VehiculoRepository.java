package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Vehiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    @Query("SELECT v FROM Vehiculo v WHERE v.activo=TRUE")
    public List<Vehiculo> findAllActivo();

    @Query("SELECT v FROM Vehiculo v WHERE v.activo=TRUE AND v.marca=:marca AND v.modelo=:modelo AND v.anioModelo=:anioModelo")
    public Optional<Vehiculo> findByMarcaModeloAnioModelo(String marca, String modelo, Integer anioModelo);

}
