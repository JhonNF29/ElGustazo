package com.polleria.ElGustazo.service;

import com.polleria.ElGustazo.dto.PlatoRequestDTO;
import com.polleria.ElGustazo.model.Plato;
import com.polleria.ElGustazo.repository.PlatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    public List<Plato> obtenerTodosLosPlatos() {
        return platoRepository.findAll();
    }

    public List<Plato> buscarPlatosEconomicos(Double precioMax) {
        return platoRepository.buscarPlatosEconomicos(precioMax);
    }

    @Transactional
    public Plato guardarPlato(PlatoRequestDTO dto) {
        Plato plato = new Plato();
        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setPrecio(dto.getPrecio());
        plato.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        return platoRepository.save(plato);
    }

    @Transactional
    public Optional<Plato> actualizarPlato(Long id, PlatoRequestDTO dto) {
        return platoRepository.findById(id).map(plato -> {
            plato.setNombre(dto.getNombre());
            plato.setDescripcion(dto.getDescripcion());
            plato.setPrecio(dto.getPrecio());
            if (dto.getDisponible() != null) {
                plato.setDisponible(dto.getDisponible());
            }
            return platoRepository.save(plato);
        });
    }

    @Transactional
    public boolean eliminarPlato(Long id) {
        if (platoRepository.existsById(id)) {
            platoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
