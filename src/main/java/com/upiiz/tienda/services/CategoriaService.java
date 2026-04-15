package com.upiiz.tienda.services;

import com.upiiz.tienda.models.Categoria;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    private List<Categoria> categorias = new ArrayList<>();
    private Long idCounter = 1L;

    public CategoriaService() {
        // Datos iniciales
        categorias.add(new Categoria(idCounter++, "Bebidas", "Aquí se agrupan todas las bebidas"));
        categorias.add(new Categoria(idCounter++, "Deportes", "Artículos deportivos"));
        categorias.add(new Categoria(idCounter++, "Cultura", "Libros y más"));
    }

    public List<Categoria> findAll() { return categorias; }

    public Categoria findById(Long id) {
        return categorias.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public void save(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(idCounter++);
            categorias.add(categoria);
        } else {
            Categoria existente = findById(categoria.getId());
            if (existente != null) {
                existente.setNombre(categoria.getNombre());
                existente.setDescripcion(categoria.getDescripcion());
            }
        }
    }

    public void delete(Long id) {
        categorias.removeIf(c -> c.getId().equals(id));
    }
}