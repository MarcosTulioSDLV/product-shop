package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.ProductRequestDto;
import com.springboot.product_shop.dtos.ProductResponseDto;
import com.springboot.product_shop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(name = "Products", description = "Operations related to product management")
@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products. Accessible by any authenticated user.")
    @GetMapping(value = "/products")
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(summary = "Get product by ID", description = "Retrieves the details of a product by its unique identifier. Accessible by any authenticated user.")
    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get product by name", description = "Retrieves a product with an exact name match. Accessible by any authenticated user.")
    @GetMapping(value = "/products/by-name")
    public ResponseEntity<ProductResponseDto> getProductByName(@RequestParam String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @Operation(summary = "Search products by name", description = "Retrieves a paginated list of products whose names contain the given substring. Accessible by any authenticated user.")
    @GetMapping(value = "/products/by-name-containing")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByNameContaining(@RequestParam String name,
                                                                                @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getProductsByNameContaining(name,pageable));
    }

    @Operation(summary = "Get products by category", description = "Retrieves a paginated list of products that belong to a specific category. Accessible by any authenticated user.")
    @GetMapping(value = "/categories/{categoryId}/products")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(@PathVariable UUID categoryId,
                                                                          @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId,pageable));
    }

    @Operation(summary = "Get products by price range", description = "Retrieves a paginated list of products whose price falls within the specified range. Accessible by any authenticated user.")
    @GetMapping(value = "/products/by-price-between")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByPriceBetween(@RequestParam(defaultValue = "0") BigDecimal minPrice,
                                                                              @RequestParam(defaultValue = "1000") BigDecimal maxPrice,
                                                                              @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getProductsByPriceBetween(minPrice,maxPrice,pageable));
    }

    @Operation(summary = "Get products by stock quantity range", description = "Retrieves a paginated list of products whose stock quantity falls within the specified range. Accessible by any authenticated user.")
    @GetMapping(value = "/products/by-stock-quantity-between")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByStockQuantityBetween(@RequestParam(defaultValue = "0") Integer minQuantity,
                                                                                      @RequestParam(defaultValue = "10") Integer maxQuantity,
                                                                                      @PageableDefault(size = 10) Pageable pageable){
         return ResponseEntity.ok(productService.getProductsByStockQuantityBetween(minQuantity,maxQuantity,pageable));
    }

    @Operation(summary = "Add new product", description = "Creates a new product. Accessible by users with MANAGER or ADMIN role.")
    @PostMapping(value = "/products")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto){
        return new ResponseEntity<>(productService.addProduct(productRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing product", description = "Updates the details of an existing product by its ID. Accessible by users with MANAGER or ADMIN role.")
    @PutMapping(value = "/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestBody @Valid ProductRequestDto productRequestDto){
        return ResponseEntity.ok(productService.updateProduct(id,productRequestDto));
    }

    // TERMINAR DIAGRAM DE CLASES ACTUAL (ultima clase hecha, Order)
    // Revisar CustomUserDetails, y ver comentarios de intellj, tambien revisa con el tutorial, manual da programacao
    // DOCUMENTAR con los controllers con @Tag y @Operation (Para dejar claro que endpoint son para que role)
    //documentado
    // UserController (ya)
    // CategoryController (ya)
    // ProductController (ya)
    // OrderController (ya)
    // OrderProductController (ya)
    // RoleController (ya)
    //  todos ya
    //TO DO: ADD JWT SECURITY

}
