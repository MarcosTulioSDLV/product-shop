package com.springboot.product_shop.repositories;

import com.springboot.product_shop.models.OrderProduct;
import com.springboot.product_shop.models.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {


}
