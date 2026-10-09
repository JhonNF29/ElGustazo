package com.polleria.ElGustazo.repository;

import com.polleria.ElGustazo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Consulta JPQL para listar pedidos por su estado actual
    @Query("SELECT p FROM Pedido p WHERE p.estado = :estado")
    List<Pedido> buscarPedidosPorEstado(@Param("estado") String estado);
}