package com.example.api_rest.controller;

import com.example.api_rest.model.Category;
import com.example.api_rest.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    // GET /api/categorias - Listar todas
    @GetMapping
    public ResponseEntity<List<Category>> obtenerTodas() {
        List<Category> categories = categoryService.obtenerTodas();
        return ResponseEntity.ok(categories);
    }
    
    // GET /api/categorias/{id} - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> obtenerPorId(@PathVariable Long id) {
        Optional<Category> category = categoryService.obtenerPorId(id);
        return category.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/categorias - Crear nueva
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Category category) {
        // Verificar si ya existe una categoría con ese nombre
        if (categoryService.existeNombre(category.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("There is already a category with that name");
        }
        
        Category newCategory = categoryService.guardar(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }
    
    // PUT /api/categorias/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Category> actualizar(@PathVariable Long id, 
                                                @Valid @RequestBody Category category) {
        Optional<Category> categoriaExistente = categoryService.obtenerPorId(id);
        
        if (categoriaExistente.isPresent()) {
            category.setId(id);
            Category categoriaActualizada = categoryService.guardar(category);
            return ResponseEntity.ok(categoriaActualizada);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/categorias/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Category> category = categoryService.obtenerPorId(id);
        
        if (category.isPresent()) {
            categoryService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}

