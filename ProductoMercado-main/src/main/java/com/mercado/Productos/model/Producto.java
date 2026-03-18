package com.mercado.Productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
    private Long id;
    private String nombre;
    private int precio;
    private String desc;
    private String proveedor;
}
