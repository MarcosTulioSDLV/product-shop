package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.CategoryRequestDto;
import com.springboot.product_shop.dtos.CategoryResponseDto;
import com.springboot.product_shop.models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CategoryService {

    Page<CategoryResponseDto> getAllCategories(Pageable pageable);

    CategoryResponseDto getCategoryById(UUID id);

    Category findCategoryById(UUID id);

    CategoryResponseDto getCategoryByName(String name);

    Page<CategoryResponseDto> getCategoriesByNameContaining(String name, Pageable pageable);

    @Transactional
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    @Transactional
    CategoryResponseDto updateCategory(UUID id, CategoryRequestDto categoryRequestDto);

    /*@Transactional
    void deleteCategory(UUID id);*/

}
