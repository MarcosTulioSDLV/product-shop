package com.springboot.product_shop.mappers;

import com.springboot.product_shop.dtos.*;
import com.springboot.product_shop.models.OrderProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderProductMapper {

    private final ModelMapper modelMapper;

    private final ProductMapper productMapper;

    @Autowired
    public OrderProductMapper(ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public OrderProductResponseDto toOrderProductResponseDto(OrderProduct orderProduct) {
        OrderProductResponseDto orderProductResponseDto = modelMapper.map(orderProduct, OrderProductResponseDto.class);

        orderProductResponseDto.setOrderId(orderProduct.getOrder().getId());

        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(orderProduct.getProduct());
        orderProductResponseDto.setProductResponseDto(productResponseDto);

        orderProductResponseDto.setProductUnitPrice(orderProduct.GetProductUnitPrice());

        return orderProductResponseDto;
    }

    public List<OrderProductResponseDto> toOrderProductResponseDtoList(List<OrderProduct> orderProductList) {
        return orderProductList.stream()
                .map(this::toOrderProductResponseDto)
                .collect(toList());
    }

    // Note: Each time this method is used, The orderSimplifiedResponseDto field must be set outside of this method,
    // since mapping it here could lead to a circular reference between OrderProductMapper and OrderMapper DTOs.
    public OrderProductWithOrderSimplifiedResponseDto toOrderProductWithOrderSimplifiedResponseDto(OrderProduct orderProduct) {
        OrderProductWithOrderSimplifiedResponseDto orderProductWithOrderSimplifiedResponseDto= modelMapper.map(orderProduct, OrderProductWithOrderSimplifiedResponseDto.class);

        orderProductWithOrderSimplifiedResponseDto.setOrderId(orderProduct.getOrder().getId());

        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(orderProduct.getProduct());
        orderProductWithOrderSimplifiedResponseDto.setProductResponseDto(productResponseDto);

        orderProductWithOrderSimplifiedResponseDto.setProductUnitPrice(orderProduct.GetProductUnitPrice());

        return orderProductWithOrderSimplifiedResponseDto;
    }

}
