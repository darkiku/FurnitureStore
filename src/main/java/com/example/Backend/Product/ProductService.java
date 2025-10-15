package com.example.Backend.Product;

import com.example.Backend.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product getProductById(long id) {
        return productRepository.findById(id);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
    public void updateProduct(Product product) {
        Product updateProduct = productRepository.findById(product.getId());
        if (updateProduct != null) {
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setImageUrl(product.getImageUrl());
        updateProduct.setCategory(product.getCategory());
        productRepository.save(updateProduct);
    }}

}