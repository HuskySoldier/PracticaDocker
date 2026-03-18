package com.example.inventario.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventario.client.ProductoClient;
import com.example.inventario.dto.ProductoDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClient productoClient;

    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario guardarInventario(Inventario inventario) {

        if (inventario.getProductoId() == null) {
            throw new RuntimeException("productoId es obligatorio");
        }

        if (inventario.getStockActual() == null || inventario.getStockActual() < 0) {
            throw new RuntimeException("Stock actual inválido");
        }

        if (inventario.getStockMinimo() == null || inventario.getStockMinimo() < 0) {
            throw new RuntimeException("Stock mínimo inválido");
        }

        if (inventario.getUbicacion() == null || inventario.getUbicacion().isBlank()) {
            throw new RuntimeException("La ubicación es obligatoria");
        }

        ProductoDTO producto = productoClient.obtenerProducto(inventario.getProductoId());
        if (producto == null) {
            throw new RuntimeException("El producto no existe en el microservicio de productos");
        }

        if (inventarioRepository.findByProductoId(inventario.getProductoId()).isPresent()) {
            throw new RuntimeException("Ya existe inventario para este producto");
        }

        inventario.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inventario);
    }

    public Inventario agregarStock(Long productoId, Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("Cantidad inválida");
        }

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        inventario.setStockActual(inventario.getStockActual() + cantidad);
        inventario.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(inventario);
    }

    public Inventario descontarStock(Long productoId, Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("Cantidad inválida");
        }

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (inventario.getStockActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        inventario.setStockActual(inventario.getStockActual() - cantidad);
        inventario.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(inventario);
    }

    public boolean hayStock(Long productoId, Integer cantidad) {

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        return inventario.getStockActual() >= cantidad;
    }
}