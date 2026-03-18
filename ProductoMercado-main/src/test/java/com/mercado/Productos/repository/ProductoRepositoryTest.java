package com.mercado.Productos.repository;

import com.mercado.Productos.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductoRepositoryTest {

    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository = new ProductoRepository();
    }

    @Test
    void findAll_deberiaRetornarProductosIniciales() {
        List<Producto> productos = productoRepository.findAll();

        assertNotNull(productos);
        assertEquals(5, productos.size());
    }

    @Test
    void findById_deberiaRetornarProductoCuandoExiste() {
        Optional<Producto> producto = productoRepository.findById(1L);

        assertTrue(producto.isPresent());
        assertEquals("Notebook Lenovo IdeaPad 3", producto.get().getNombre());
    }

    @Test
    void findById_deberiaRetornarEmptyCuandoNoExiste() {
        Optional<Producto> producto = productoRepository.findById(999L);

        assertTrue(producto.isEmpty());
    }

    @Test
    void save_deberiaGuardarProductoYAsignarIdSiNoTiene() {
        Producto nuevo = new Producto(null, "Parlante JBL", 89990,
                "Parlante bluetooth portátil", "JBL");

        Producto guardado = productoRepository.save(nuevo);

        assertNotNull(guardado.getId());
        assertEquals("Parlante JBL", guardado.getNombre());

        List<Producto> productos = productoRepository.findAll();
        assertEquals(6, productos.size());
    }
}