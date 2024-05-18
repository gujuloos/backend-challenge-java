package org.challenge.candidate.repository;

import org.challenge.candidate.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findProductByCodeTest() {
        Product product = this.productRepository.findProductByCode("MUG");
        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Triggerise Mug", product.getName());
    }

    @Test
    void findAllProductsTest() {
        List<Product> products = this.productRepository.findAll();
        assertNotNull(products);
        assertEquals(3, products.size());
    }

}
