package com.example.Backend.visitor;

public interface Visitable {
    void accept(ProductVisitor visitor);
}