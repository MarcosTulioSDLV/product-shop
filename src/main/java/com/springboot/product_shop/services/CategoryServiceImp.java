package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.CategoryRequestDto;
import com.springboot.product_shop.dtos.CategoryResponseDto;
import com.springboot.product_shop.exceptions.CategoryNameExistsException;
import com.springboot.product_shop.exceptions.CategoryNotFoundException;
import com.springboot.product_shop.mappers.CategoryMapper;
import com.springboot.product_shop.models.Category;
import com.springboot.product_shop.repositories.CategoryRepository;
import com.springboot.product_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImp implements CategoryService{

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Page<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toCategoryResponseDto);
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID id) {
        Category category= findCategoryById(id);
        return categoryMapper.toCategoryResponseDto(category);
    }

    public Category findCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id: " + id + " not found!"));
    }

    @Override
    public CategoryResponseDto getCategoryByName(String name) {
        Category category= categoryRepository.findByNameIgnoreCase(name).orElseThrow(()-> new CategoryNotFoundException("Category with name: "+name+" not found!"));
        return categoryMapper.toCategoryResponseDto(category);
    }

    @Override
    public Page<CategoryResponseDto> getCategoriesByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByNameContainingIgnoreCase(name,pageable).map(categoryMapper::toCategoryResponseDto);
    }

    @Override
    @Transactional
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        Category category= categoryMapper.toCategory(categoryRequestDto);
        validateUniqueFields(category);
        return categoryMapper.toCategoryResponseDto(categoryRepository.save(category));
    }

    private void validateUniqueFields(Category category) {
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new CategoryNameExistsException("Category with name: "+ category.getName()+" already exists!");
        }
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto categoryRequestDto) {
        Category category= categoryMapper.toCategory(categoryRequestDto);

        Category recoveredCategory= findCategoryById(id);
        validateFieldsUpdateConflict(category, recoveredCategory);

        BeanUtils.copyProperties(category,recoveredCategory,"id");
        return categoryMapper.toCategoryResponseDto(recoveredCategory);
    }

    private void validateFieldsUpdateConflict(Category category, Category recoveredCategory) {
        if(categoryNameExistsForAnotherCategory(category.getName(), recoveredCategory)){
            throw new CategoryNameExistsException("Category with name: "+ category.getName()+" already exists!");
        }
    }

    private boolean categoryNameExistsForAnotherCategory(String name, Category recoveredCategory) {
        return categoryRepository.existsByNameIgnoreCaseAndIdNot(name,recoveredCategory.getId());
    }

    /*@Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category= findCategoryById(id);
        productRepository.deleteAllByCategory(category);
        categoryRepository.delete(category);
    }*/

}
