package com.example.shopping.repository;
import com.example.shopping.entity.data.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findByIdentificator(String identificator);
    Page findByIdentificatorAndIdNot(String identificator, int id);

    List<Page> findAllByOrderBySequenceNumberAsc();
}
