package com.springboot.product_shop.mappers;

import com.springboot.product_shop.dtos.CategoryRequestDto;
import com.springboot.product_shop.dtos.CategoryResponseDto;
import com.springboot.product_shop.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryResponseDto toCategoryResponseDto(Category category){
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public Category toCategory(CategoryRequestDto categoryRequestDto) {
        return modelMapper.map(categoryRequestDto, Category.class);
    }

}

