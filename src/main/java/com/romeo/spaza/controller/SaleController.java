package com.romeo.spaza.controller;

import com.romeo.spaza.dto.SaleRequest;
import com.romeo.spaza.dto.SaleResponse;
import com.romeo.spaza.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'STAFF')")
    public ResponseEntity<SaleResponse> recordSale(@Valid @RequestBody SaleRequest request) {
        return new ResponseEntity<>(saleService.recordSale(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }
}
