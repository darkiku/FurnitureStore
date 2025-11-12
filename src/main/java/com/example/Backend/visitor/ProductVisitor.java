package com.example.Backend.visitor;

import com.example.Backend.Product.Product;

public interface ProductVisitor {
    void visit(Product product);
    double getResult();
}