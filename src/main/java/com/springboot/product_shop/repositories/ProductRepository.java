package com.springboot.product_shop.repositories;

import com.springboot.product_shop.models.Category;
import com.springboot.product_shop.models.Order;
import com.springboot.product_shop.models.OrderProduct;
import com.springboot.product_shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

    void deleteAllByCategory(Category category);

    Optional<Product> findByNameIgnoreCase(String name);

    Page<Product> findByNameContainingIgnoreCase(String name,Pageable pageable);

    Page<Product> findAllByCategory(Category category,Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByStockQuantityBetween(Integer minQuantity, Integer maxQuantity, Pageable pageable);

}
