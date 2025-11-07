package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Vehiculo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Optional<Vehiculo> findByMarcaAndModeloAndAnioModelo(String marca, String modelo, Integer anioModelo);

}
