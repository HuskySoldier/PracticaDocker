package com.mercado.Productos.service;

import com.mercado.Productos.model.Producto;
import com.mercado.Productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void crearProducto_deberiaGuardarYRetornarProducto() {
        Producto entrada = new Producto(null, "Mouse Logitech G203", 19990,
                "Mouse gamer RGB", "Logitech");
        Producto guardado = new Producto(1L, "Mouse Logitech G203", 19990,
                "Mouse gamer RGB", "Logitech");

        when(productoRepository.save(entrada)).thenReturn(guardado);

        Producto resultado = productoService.crearProducto(entrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Mouse Logitech G203", resultado.getNombre());
        assertEquals(19990, resultado.getPrecio());

        verify(productoRepository, times(1)).save(entrada);
    }

    @Test
    void listarProductos_deberiaRetornarLista() {
        List<Producto> productos = List.of(
                new Producto(1L, "Mouse", 19990, "Mouse gamer", "Logitech"),
                new Producto(2L, "Teclado", 45990, "Teclado mecánico", "Redragon")
        );

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Mouse", resultado.get(0).getNombre());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void obtenerProductoPorId_deberiaRetornarProductoCuandoExiste() {
        Producto producto = new Producto(1L, "Monitor Samsung", 129990,
                "Monitor Full HD", "Samsung");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Monitor Samsung", resultado.getNombre());

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerProductoPorId_deberiaRetornarNullCuandoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = productoService.obtenerProductoPorId(99L);

        assertNull(resultado);
        verify(productoRepository, times(1)).findById(99L);
    }
}