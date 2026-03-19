package com.example.inventario.client;


import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.example.inventario.dto.ProductoDTO;

@Component
public class ProductoClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${producto.service.url}")
    private String productoServiceUrl;

    public ProductoDTO obtenerProducto(Long productoId) {
        try {
            return restTemplate.getForObject(
                productoServiceUrl + "/" + productoId,
                ProductoDTO.class
            );
        } catch (Exception e) {
            return null;
        }
    }
}