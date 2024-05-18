package org.challenge.candidate.processor;

import org.challenge.candidate.entity.Cart;
import org.challenge.candidate.entity.CartItem;
import org.challenge.candidate.entity.Product;
import org.challenge.candidate.exception.NoProductFoundException;
import org.challenge.candidate.model.CheckoutResponseDTO;
import org.challenge.candidate.model.ScanItemRequestDTO;
import org.challenge.candidate.repository.CartRepository;
import org.challenge.candidate.repository.ProductRepository;
import org.challenge.messaging.Processor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ScanProcessor implements Processor {

    private final String PRODUCT_MUG = "MUG";
    private final String PRODUCT_T_SHIRT = "TSHIRT";

    private final BigDecimal SEVENTY_PERCENT = BigDecimal.valueOf(0.7);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public ScanProcessor(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CheckoutResponseDTO scanItems(List<ScanItemRequestDTO> items) {
        Cart cart = this.addItemsToCart(items);
        this.applyDiscounts(cart);

        // calculate total
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            cartTotal = cartTotal.add(cartItem.getPrice()).setScale(2, RoundingMode.HALF_UP);
        }

        cart.setTotal(cartTotal);
        this.cartRepository.save(cart);

        return new CheckoutResponseDTO(cartTotal);
    }

    private Cart addItemsToCart(List<ScanItemRequestDTO> items) {
        Cart cart = new Cart();

        for (ScanItemRequestDTO item: items) {
            Product processedProduct = (Product) this.process(item.getItemCode());

            CartItem cartItem = new CartItem();
            cartItem.setProduct(processedProduct);
            cartItem.setQuantity(1);
            cartItem.setPrice(processedProduct.getPrice());

            boolean foundExistingItem = false;
            for (CartItem existingItem: cart.getCartItems()) {
                if (existingItem.equals(cartItem)) {
                    foundExistingItem = true;
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    existingItem.setPrice(existingItem.getProduct().getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())).setScale(2, RoundingMode.HALF_UP));
                }
            }

            if (!foundExistingItem) {
                cart.getCartItems().add(cartItem);
            }
        }
        return cart;
    }

    private void applyDiscounts(Cart cart) {
        // apply discounts & calculate total
        boolean mugDiscountApplied = false;
        boolean hasOneMug = false;
        boolean hasMoreThan2TShirts = false;

        for (CartItem cartItem: cart.getCartItems()) {
            BigDecimal itemQuantity = BigDecimal.valueOf(cartItem.getQuantity()).setScale(0, RoundingMode.HALF_UP);

            if (cartItem.getProduct().getCode().equals(PRODUCT_MUG)) {
                hasOneMug = true;
            }

            if (cartItem.getProduct().getCode().equals(PRODUCT_MUG) && cartItem.getQuantity() >= 2) {
                BigDecimal totalNumberOfMugsForPrice = itemQuantity.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
                BigDecimal cartItemPrice = totalNumberOfMugsForPrice.multiply(cartItem.getProduct().getPrice()).setScale(2, RoundingMode.HALF_UP);
                cartItem.setPrice(cartItemPrice);
                mugDiscountApplied = true;
            }

            if (cartItem.getProduct().getCode().equals(PRODUCT_T_SHIRT) && cartItem.getQuantity() >= 3) {
                BigDecimal priceOfShirts = cartItem.getPrice();
                BigDecimal cartItemPrice = priceOfShirts.multiply(SEVENTY_PERCENT).setScale(2, RoundingMode.HALF_UP);
                cartItem.setPrice(cartItemPrice);
            }

            if (cartItem.getProduct().getCode().equals(PRODUCT_T_SHIRT) && cartItem.getQuantity() >= 2) {
                hasMoreThan2TShirts = true;
            }
        }

        if (!mugDiscountApplied && hasOneMug && hasMoreThan2TShirts) {
            for (CartItem cartItem: cart.getCartItems()) {
                if (cartItem.getProduct().getCode().equals(PRODUCT_MUG)) {
                    cartItem.setPrice(cartItem.getPrice().subtract(cartItem.getProduct().getPrice()));
                }
            }
        }
    }

    @Override
    public Object process(String input) {
        if (input != null) {
            Product product = this.productRepository.findProductByCode(input);
            if (product == null) {
                throw new NoProductFoundException(input);
            }
            return product;
        }
        throw new NoProductFoundException();
    }
}
