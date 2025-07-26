package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.ProductRequestDto;
import com.springboot.product_shop.dtos.ProductResponseDto;
import com.springboot.product_shop.exceptions.ProductNameExistsException;
import com.springboot.product_shop.exceptions.ProductNameNotFoundException;
import com.springboot.product_shop.exceptions.ProductNotFoundException;
import com.springboot.product_shop.mappers.ProductMapper;
import com.springboot.product_shop.models.*;
import com.springboot.product_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    //TO DO? DEJAR O BORRAR?
    /*private UserEntity getCurrentLoggedUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            System.out.println("Unauthorized access!");
            throw new AccessDeniedException("Unauthorized access!");
        }
        if(!(userDetails instanceof CustomUserDetails customUserDetails)){
            System.out.println("UserDetails is not an instance of CustomUserDetails!");
            throw new ClassCastException("UserDetails is not an instance of CustomUserDetails!");
        }
        return customUserDetails.getUser();
    }*/

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductResponseDto);
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {
        Product product= findProductById(id);
        return productMapper.toProductResponseDto(product);
    }

    public Product findProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " not found!"));
    }

    @Override
    public ProductResponseDto getProductByName(String name) {
        Product product= productRepository.findByNameIgnoreCase(name).orElseThrow(()-> new ProductNameNotFoundException("Product with name: "+name+" not found!"));
        return productMapper.toProductResponseDto(product);
    }

    @Override
    public Page<ProductResponseDto> getProductsByNameContaining(String name,Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name,pageable).map(productMapper::toProductResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(UUID categoryId,Pageable pageable) {
        Category category= categoryService.findCategoryById(categoryId);
        return productRepository.findAllByCategory(category,pageable).map(productMapper::toProductResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByPriceBetween(BigDecimal minPrice,BigDecimal maxPrice,Pageable pageable) {
        return productRepository.findByPriceBetween(minPrice,maxPrice,pageable).map(productMapper::toProductResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByStockQuantityBetween(Integer minQuantity, Integer maxQuantity, Pageable pageable) {
        return productRepository.findByStockQuantityBetween(minQuantity,maxQuantity,pageable).map(productMapper::toProductResponseDto);
    }

    @Override
    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Product product= productMapper.toProduct(productRequestDto);

        validateUniqueFields(product);

        Category category= categoryService.findCategoryById(productRequestDto.getCategoryId());
        product.setCategory(category);

        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    private void validateUniqueFields(Product product) {
        if(productRepository.existsByNameIgnoreCase(product.getName())){
            throw new ProductNameExistsException("Product with name: "+ product.getName()+" already exists!");
        }
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto productRequestDto) {
        Product product= productMapper.toProduct(productRequestDto);

        Product recoveredProduct= findProductById(id);
        validateFieldsUpdateConflict(product, recoveredProduct);

        Category category= categoryService.findCategoryById(productRequestDto.getCategoryId());
        product.setCategory(category);

        BeanUtils.copyProperties(product,recoveredProduct,"id");
        return productMapper.toProductResponseDto(recoveredProduct);
    }

    private void validateFieldsUpdateConflict(Product product, Product recoveredProduct) {
        if(productNameExistsForAnotherProduct(product.getName(), recoveredProduct)){
            throw new ProductNameExistsException("Product with name: "+ product.getName()+" already exists!");
        }
    }

    private boolean productNameExistsForAnotherProduct(String name, Product recoveredProduct) {
        return productRepository.existsByNameIgnoreCaseAndIdNot(name,recoveredProduct.getId());
    }



}
