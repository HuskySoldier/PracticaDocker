package com.example.inventario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inventario.model.Inventario;
import com.example.inventario.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventario() {
        return ResponseEntity.ok(inventarioService.listarInventario());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> obtenerPorProducto(@PathVariable Long productoId) {
        Optional<Inventario> inventario = inventarioService.buscarPorProductoId(productoId);
        return inventario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardarInventario(@RequestBody Inventario inventario) {
        try {
            return ResponseEntity.ok(inventarioService.guardarInventario(inventario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{productoId}/agregar")
    public ResponseEntity<?> agregarStock(@PathVariable Long productoId,
                                           @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(inventarioService.agregarStock(productoId, cantidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{productoId}/descontar")
    public ResponseEntity<?> descontarStock(@PathVariable Long productoId,
                                             @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(inventarioService.descontarStock(productoId, cantidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{productoId}/disponible/{cantidad}")
    public ResponseEntity<Boolean> verificarStock(@PathVariable Long productoId,
                                                  @PathVariable Integer cantidad) {
        return ResponseEntity.ok(inventarioService.hayStock(productoId, cantidad));
    }
}
