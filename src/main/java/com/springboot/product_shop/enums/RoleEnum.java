package com.springboot.product_shop.enums;

public enum RoleEnum {
    ADMIN,    // Full access: can manage users, products, orders, etc.
    CUSTOMER, // End user: can browse, purchase products, and manage their own account.
    MANAGER   // Operational role: can manage products, inventory, and orders.
}
