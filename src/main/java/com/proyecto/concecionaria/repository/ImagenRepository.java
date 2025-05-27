package com.proyecto.concecionaria.repository;

import com.proyecto.concecionaria.entity.Imagen;
import java.awt.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer> {

}
