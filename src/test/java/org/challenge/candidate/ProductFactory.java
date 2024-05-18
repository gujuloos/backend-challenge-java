package org.challenge.candidate;

import org.challenge.candidate.entity.Product;

import java.math.BigDecimal;

public class ProductFactory {

    public Product createMugProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("MUG");
        product.setName("Triggerise Mug");
        product.setPrice(BigDecimal.valueOf(4.00));
        return product;
    }

    public Product createTShirtProduct() {
        Product product = new Product();
        product.setId(2L);
        product.setCode("TSHIRT");
        product.setName("Triggerise T-Shirt");
        product.setPrice(BigDecimal.valueOf(21.00));
        return product;
    }

    public Product createUSBKeyProduct() {
        Product product = new Product();
        product.setId(3L);
        product.setCode("USBKEY");
        product.setName("Triggerise USB Key");
        product.setPrice(BigDecimal.valueOf(10.00));
        return product;
    }
}
