package com.polleria.ElGustazo.repository;

import com.polleria.ElGustazo.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    // Consulta JPQL para filtrar platos económicos disponibles
    @Query("SELECT p FROM Plato p WHERE p.disponible = true AND p.precio <= :precioMax")
    List<Plato> buscarPlatosEconomicos(@Param("precioMax") Double precioMax);
}