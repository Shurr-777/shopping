package com.example.shopping.repository;

import com.example.shopping.entity.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

    List<Category> findAllByOrderBySequenceNumberAsc();

    Category findByIdentificator(String identificator);
}
