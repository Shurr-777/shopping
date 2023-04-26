package com.example.shopping.entity.data;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min=2, message = "Name must be at least 2 characters long")
    private String name;

    private String identificator;

    @Size(min=5, message = "Description must be at least 5 characters long")
    private String description;

    private String image;

    @Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "Expected format: 5, 5.05, 5.10")
    private String price;

    @Pattern(regexp = "^[1-9][0-9]*", message = "Please choose a category")
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "url")
    private String url;
}
