package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.ProductRequestDto;
import com.springboot.product_shop.dtos.ProductResponseDto;
import com.springboot.product_shop.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public interface ProductService {

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProductById(UUID id);

    Product findProductById(UUID id);

    ProductResponseDto getProductByName(String name);

    Page<ProductResponseDto> getProductsByNameContaining(String name,Pageable pageable);

    Page<ProductResponseDto> getProductsByCategory(UUID categoryId,Pageable pageable);

    Page<ProductResponseDto> getProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice,Pageable pageable);

    Page<ProductResponseDto> getProductsByStockQuantityBetween(Integer minQuantity, Integer maxQuantity, Pageable pageable);

    @Transactional
    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    @Transactional
    ProductResponseDto updateProduct(UUID id, ProductRequestDto productRequestDto);

}
