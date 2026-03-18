package com.mercado.Productos.repository;

import org.springframework.stereotype.Repository;

import com.mercado.Productos.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductoRepository {

    // Nuestra "Base de Datos" temporal
    private final List<Producto> db = new ArrayList<>();

    
    // Para generar IDs de forma segura
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductoRepository() {
        db.add(new Producto(idGenerator.getAndIncrement(),
                "Notebook Lenovo IdeaPad 3",
                650000,
                "Notebook 15 pulgadas, 8GB RAM, SSD 256GB",
                "Lenovo Chile"));

        db.add(new Producto(idGenerator.getAndIncrement(),
                "Mouse Logitech G203",
                19990,
                "Mouse gamer RGB con sensor de alta precisión",
                "Logitech"));

        db.add(new Producto(idGenerator.getAndIncrement(),
                "Teclado Mecánico Redragon Kumara",
                45990,
                "Teclado mecánico RGB switches blue",
                "Redragon"));

        db.add(new Producto(idGenerator.getAndIncrement(),
                "Monitor Samsung 24 pulgadas",
                129990,
                "Monitor Full HD 75Hz con HDMI",
                "Samsung"));

        db.add(new Producto(idGenerator.getAndIncrement(),
                "Audífonos HyperX Cloud Stinger",
                54990,
                "Audífonos gamer con micrófono y sonido envolvente",
                "HyperX"));
    }

    // Guardar un producto
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(idGenerator.getAndIncrement());
        }
        db.add(producto);
        return producto;
    }

    // Listar todos
    public List<Producto> findAll() {
        return new ArrayList<>(db); // Retornamos copia para proteger la original
    }

    // Buscar por ID
    public Optional<Producto> findById(Long id) {
        return db.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }



}
