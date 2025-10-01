package com.example.api_rest.controller;

import com.example.api_rest.model.Habit;
import com.example.api_rest.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*")
public class HabitController {
    
    @Autowired
    private HabitService habitService;
    
    // GET /api/habits - Obtener todos
    @GetMapping
    public ResponseEntity<List<Habit>> obtenerTodos() {
        List<Habit> habits = habitService.obtenerTodos();
        return ResponseEntity.ok(habits);
    }
    
    // GET /api/habits/active - Obtener solo activos
    @GetMapping("/active")
    public ResponseEntity<List<Habit>> obtenerActivos() {
        List<Habit> habits = habitService.obtenerActivos();
        return ResponseEntity.ok(habits);
    }
    
    // GET /api/habits/{id} - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Habit> obtenerPorId(@PathVariable Long id) {
        Optional<Habit> habit = habitService.obtenerPorId(id);
        return habit.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/habits - Crear nuevo
    @PostMapping
    public ResponseEntity<Habit> crear(@Valid @RequestBody Habit habit) {
        Habit nuevoHabit = habitService.guardar(habit);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHabit);
    }
    
    // PUT /api/habits/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Habit> actualizar(@PathVariable Long id, @Valid @RequestBody Habit habit) {
        Optional<Habit> habitExistente = habitService.obtenerPorId(id);
        
        if (habitExistente.isPresent()) {
            habit.setId(id);
            Habit habitActualizado = habitService.guardar(habit);
            return ResponseEntity.ok(habitActualizado);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/habits/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Habit> habit = habitService.obtenerPorId(id);
        
        if (habit.isPresent()) {
            habitService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // PATCH /api/habits/{id}/deactivate - Desactivar
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Habit> desactivar(@PathVariable Long id) {
        Habit habit = habitService.desactivar(id);
        
        if (habit != null) {
            return ResponseEntity.ok(habit);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/habits/frecuencia/{frecuencia} - Por frecuencia
    @GetMapping("/frecuencia/{frecuencia}")
    public ResponseEntity<List<Habit>> obtenerPorFrecuencia(@PathVariable String frecuencia) {
        List<Habit> habits = habitService.obtenerPorFrecuencia(frecuencia);
        return ResponseEntity.ok(habits);
    }
    
    // NUEVO: GET /api/habits/categoria/{categoriaId} - Por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Habit>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        List<Habit> habits = habitService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(habits);
    }
}