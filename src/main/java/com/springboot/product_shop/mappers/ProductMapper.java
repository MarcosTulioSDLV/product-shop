package com.springboot.product_shop.mappers;

import com.springboot.product_shop.dtos.CategoryResponseDto;
import com.springboot.product_shop.dtos.ProductRequestDto;
import com.springboot.product_shop.dtos.ProductResponseDto;
import com.springboot.product_shop.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper, CategoryMapper categoryMapper) {
        this.modelMapper = modelMapper;
        this.categoryMapper = categoryMapper;
    }

    public ProductResponseDto toProductResponseDto(Product product){
        ProductResponseDto productResponseDto= modelMapper.map(product, ProductResponseDto.class);
        CategoryResponseDto categoryResponseDto= categoryMapper.toCategoryResponseDto(product.getCategory());
        productResponseDto.setCategoryResponseDto(categoryResponseDto);
        return productResponseDto;
    }

    public Product toProduct(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto,Product.class);
    }

}
