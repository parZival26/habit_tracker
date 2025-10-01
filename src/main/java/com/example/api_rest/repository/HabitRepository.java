package com.example.api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_rest.model.Habit;
import java.util.List;
@Repository
public interface HabitRepository extends JpaRepository<Habit, Long>{

    List<Habit> findByActivo(Boolean activo);
    List<Habit> findByFrecuency(String frecuency);
    List<Habit> findByNameContainingIgnoreCase(String name);
    List<Habit> findByCategoryId(Long categoryId);


    
}
