package com.example.Backend.visitor;

import com.example.Backend.Product.Product;

public class PriceVisitor implements ProductVisitor {
    private double totalPrice = 0.0;
    private double totalDiscount = 0.0;
    @Override
    public void visit(Product product) {
        double price = product.getPrice() != null ? product.getPrice() : 0.0;
        totalPrice += price;
        double discount = calculateDiscount(product, price);
        totalDiscount += discount;
        System.out.println( product.getName() +
                "Price: $" + price +
                "Discount: $" + String.format("%.2f", discount));
    }

    private double calculateDiscount(Product product, double price) {
        String category = product.getCategory().toLowerCase();

        if (category.equals("divan")) {
            return price *0.15;
        } else if (category.equals("stol")) {
            return price *0.20;
        } else if (category.equals("stul")) {
            return price * 0.10;
        } else if (category.equals("bed")) {
            return price *0.12;
        } else {
            return price *0.05;
        }
    }
    @Override
    public double getResult() {
        return totalPrice - totalDiscount;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public double getTotalDiscount() {
        return totalDiscount;
    }
}