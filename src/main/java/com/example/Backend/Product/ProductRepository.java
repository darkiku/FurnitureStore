package com.example.Backend.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    void deleteById(long id);
    Product findById(long id);
    List<Product> findAll();
}
