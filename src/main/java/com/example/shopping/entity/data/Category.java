package com.example.shopping.entity.data;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="categories")
@Data
@Transactional
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min=2, message="Name must be at least 2 characters long")
    private String name;

    private String description;

    private String identificator;

    private int sequenceNumber;

    public Category(int id) {
        this.id = id;
    }

    public Category() {
    }
}
