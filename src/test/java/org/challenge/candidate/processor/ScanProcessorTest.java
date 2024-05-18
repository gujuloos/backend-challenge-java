package org.challenge.candidate.processor;

import org.challenge.candidate.ProductFactory;
import org.challenge.candidate.exception.NoProductFoundException;
import org.challenge.candidate.model.ScanItemRequestDTO;
import org.challenge.candidate.repository.CartRepository;
import org.challenge.candidate.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScanProcessorTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;

    private ProductFactory productFactory;

    private ScanProcessor scanProcessor;

    @BeforeEach
    void setup() {
        this.scanProcessor = new ScanProcessor(cartRepository, productRepository);
        this.productFactory = new ProductFactory();
    }

    @Test
    void scanItemsWithInvalidProductTest() {
        var request = List.of(new ScanItemRequestDTO("GOLFSHIRT"));
        var response = assertThrows(NoProductFoundException.class, () -> this.scanProcessor.scanItems(request));
        assertEquals("Cannot find product with code GOLFSHIRT", response.getMessage());
    }

    @Test
    void scanItemsTest() {
        when(this.productRepository.findProductByCode("MUG")).thenReturn(this.productFactory.createMugProduct());
        when(this.productRepository.findProductByCode("TSHIRT")).thenReturn(this.productFactory.createTShirtProduct());
        when(this.productRepository.findProductByCode("USBKEY")).thenReturn(this.productFactory.createUSBKeyProduct());

        var request = List.of(new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("USBKEY"));
        var response = this.scanProcessor.scanItems(request);
        assertEquals(BigDecimal.valueOf(35.00).setScale(2, RoundingMode.HALF_UP), response.getValue());
    }

    @Test
    void scanItemsDoubleMugTest() {
        when(this.productRepository.findProductByCode("MUG")).thenReturn(this.productFactory.createMugProduct());
        when(this.productRepository.findProductByCode("TSHIRT")).thenReturn(this.productFactory.createTShirtProduct());

        var request = List.of(new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("MUG"));
        var response = this.scanProcessor.scanItems(request);
        assertEquals(BigDecimal.valueOf(25.00).setScale(2, RoundingMode.HALF_UP), response.getValue());
    }

    @Test
    void scanItemsMultipleShirtsWithMugTest() {
        when(this.productRepository.findProductByCode("MUG")).thenReturn(this.productFactory.createMugProduct());
        when(this.productRepository.findProductByCode("TSHIRT")).thenReturn(this.productFactory.createTShirtProduct());

        var request = List.of(new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("TSHIRT") ,new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("TSHIRT"));
        var response = this.scanProcessor.scanItems(request);
        assertEquals(BigDecimal.valueOf(58.80).setScale(2, RoundingMode.HALF_UP), response.getValue());
    }

    @Test
    void scanItemsMultipleShirtsAndMultipleMugsAndUSBKeyTest() {
        when(this.productRepository.findProductByCode("MUG")).thenReturn(this.productFactory.createMugProduct());
        when(this.productRepository.findProductByCode("TSHIRT")).thenReturn(this.productFactory.createTShirtProduct());
        when(this.productRepository.findProductByCode("USBKEY")).thenReturn(this.productFactory.createUSBKeyProduct());

        var request = List.of(new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("MUG"), new ScanItemRequestDTO("USBKEY"), new ScanItemRequestDTO("TSHIRT"), new ScanItemRequestDTO("TSHIRT"));
        var response = this.scanProcessor.scanItems(request);
        assertEquals(BigDecimal.valueOf(62.10).setScale(2, RoundingMode.HALF_UP), response.getValue());
    }
}
