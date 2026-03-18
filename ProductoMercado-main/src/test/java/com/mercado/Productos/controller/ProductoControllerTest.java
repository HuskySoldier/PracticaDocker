package com.mercado.Productos.controller;


import com.mercado.Productos.model.Producto;
import com.mercado.Productos.service.ProductoService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void getAllProductos_deberiaRetornar200CuandoHayDatos() throws Exception {
        List<Producto> productos = List.of(
                new Producto(1L, "Mouse Logitech G203", 19990, "Mouse gamer RGB", "Logitech"),
                new Producto(2L, "Monitor Samsung 24 pulgadas", 129990, "Monitor Full HD 75Hz", "Samsung")
        );

        when(productoService.listarProductos()).thenReturn(productos);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Mouse Logitech G203"))
                .andExpect(jsonPath("$[1].nombre").value("Monitor Samsung 24 pulgadas"));
    }

    @Test
    void getAllProductos_deberiaRetornar204CuandoListaEstaVacia() throws Exception {
        when(productoService.listarProductos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProductoById_deberiaRetornar200CuandoExiste() throws Exception {
        Producto producto = new Producto(
                1L,
                "Notebook Lenovo IdeaPad 3",
                650000,
                "Notebook 15 pulgadas, 8GB RAM, SSD 256GB",
                "Lenovo Chile"
        );

        when(productoService.obtenerProductoPorId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Notebook Lenovo IdeaPad 3"))
                .andExpect(jsonPath("$.precio").value(650000))
                .andExpect(jsonPath("$.proveedor").value("Lenovo Chile"));
    }

    @Test
    void getProductoById_deberiaRetornar404CuandoNoExiste() throws Exception {
        when(productoService.obtenerProductoPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProducto_deberiaRetornar201CuandoSeCreaCorrectamente() throws Exception {
        Producto entrada = new Producto(
                null,
                "Audífonos HyperX Cloud Stinger",
                54990,
                "Audífonos gamer con micrófono",
                "HyperX"
        );

        Producto guardado = new Producto(
                6L,
                "Audífonos HyperX Cloud Stinger",
                54990,
                "Audífonos gamer con micrófono",
                "HyperX"
        );

        when(productoService.crearProducto(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.nombre").value("Audífonos HyperX Cloud Stinger"))
                .andExpect(jsonPath("$.precio").value(54990))
                .andExpect(jsonPath("$.proveedor").value("HyperX"));
    }
}