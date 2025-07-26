package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.CategoryRequestDto;
import com.springboot.product_shop.dtos.CategoryResponseDto;
import com.springboot.product_shop.services.CategoryService;
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

import java.util.UUID;

@Tag(name = "Categories", description = "Operations related to product categories management")
@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories", description = "Returns a paginated list of all categories. Accessible by any authenticated user.")
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDto>> getAllCategories(@PageableDefault(size = 10) Pageable pageable){
       return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @Operation(summary = "Get category by ID", description = "Returns category details by its ID. Accessible by any authenticated user.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Get category by name", description = "Returns a category that exactly matches the given name. Accessible by any authenticated user.")
    @GetMapping(value = "/by-name")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @Operation(summary = "Search categories by name", description = "Returns a paginated list of categories that contain the given name. Accessible by any authenticated user.")
    @GetMapping(value = "/by-name-containing")
    public ResponseEntity<Page<CategoryResponseDto>> getCategoriesByNameContaining(@RequestParam String name,
                                                                                   @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(categoryService.getCategoriesByNameContaining(name,pageable));
    }

    @Operation(summary = "Add new category", description = "Creates a new product category. Accessible by ADMIN and MANAGER roles.")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update category", description = "Updates an existing category by ID. Accessible by ADMIN and MANAGER roles.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID id,
                                                              @RequestBody @Valid CategoryRequestDto categoryRequestDto){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryRequestDto));
    }

    /*@DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with id: "+id+" deleted successfully!");
    }*/

}
