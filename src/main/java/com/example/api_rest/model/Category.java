package com.example.api_rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name of category is required")
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 7)
    private String color; // Hex color: #FF5733
    
    @Column(length = 50)
    private String icono; // Nombre de icono: "heart", "star", etc.
    
    // Relación One-to-Many con Habit
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore // Evita recursión infinita al serializar JSON
    private List<Habit> habits;
}