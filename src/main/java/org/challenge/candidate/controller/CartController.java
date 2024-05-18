package org.challenge.candidate.controller;

import org.challenge.candidate.model.CheckoutResponseDTO;
import org.challenge.candidate.model.ScanItemRequestDTO;
import org.challenge.candidate.processor.ScanProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final ScanProcessor scanProcessor;

    public CartController(ScanProcessor scanProcessor) {
        this.scanProcessor = scanProcessor;
    }

    @PostMapping(value = "/scan-and-checkout", produces = {"application/vnd.tiko.v1+json"})
    public CheckoutResponseDTO scanItems(@RequestBody List<ScanItemRequestDTO> request) {
        return this.scanProcessor.scanItems(request);
    }
}
