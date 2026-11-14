package com.polleria.ElGustazo.controller;

import com.polleria.ElGustazo.dto.PlatoRequestDTO;
import com.polleria.ElGustazo.model.Plato;
import com.polleria.ElGustazo.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public ResponseEntity<List<Plato>> listarPlatos() {
        return ResponseEntity.ok(platoService.obtenerTodosLosPlatos());
    }

    @GetMapping("/economicos")
    public ResponseEntity<List<Plato>> listarPlatosEconomicos(@RequestParam(defaultValue = "25.0") Double precioMax) {
        return ResponseEntity.ok(platoService.buscarPlatosEconomicos(precioMax));
    }

    @PostMapping
    public ResponseEntity<Plato> crearPlato(@Valid @RequestBody PlatoRequestDTO dto) {
        return new ResponseEntity<>(platoService.guardarPlato(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id, @Valid @RequestBody PlatoRequestDTO dto) {
        return platoService.actualizarPlato(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long id) {
        if (platoService.eliminarPlato(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
