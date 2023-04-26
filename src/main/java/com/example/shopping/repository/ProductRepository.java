package com.example.shopping.repository;
import com.example.shopping.entity.data.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByIdentificator(String identificator);

    Product findByIdentificatorAndIdNot(String identificator, int id);

    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByCategoryId(String categoryId, Pageable pageable);

    long countByCategoryId(String categoryId);
}
