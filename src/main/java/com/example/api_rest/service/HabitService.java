package com.example.api_rest.service;

import com.example.api_rest.model.Habit;
import com.example.api_rest.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    
    @Autowired
    private HabitRepository habitRepository;
    
    public List<Habit> obtenerTodos() {
        return habitRepository.findAll();
    }
    
    public List<Habit> obtenerActivos() {
        return habitRepository.findByActivo(true);
    }
    
    public Optional<Habit> obtenerPorId(Long id) {
        return habitRepository.findById(id);
    }
    
    public Habit guardar(Habit habit) {
        return habitRepository.save(habit);
    }
    
    public void eliminar(Long id) {
        habitRepository.deleteById(id);
    }
    
    public Habit desactivar(Long id) {
        Optional<Habit> habitOpt = habitRepository.findById(id);
        if (habitOpt.isPresent()) {
            Habit habit = habitOpt.get();
            habit.setActivo(false);
            return habitRepository.save(habit);
        }
        return null;
    }
    
    public List<Habit> obtenerPorFrecuencia(String frecuency) {
        return habitRepository.findByFrecuency(frecuency);
    }
    
    // NUEVO: Obtener hábitos por categoría
    public List<Habit> obtenerPorCategoria(Long categoriaId) {
        return habitRepository.findByCategoryId(categoriaId);
    }
}
