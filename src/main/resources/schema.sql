CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- Ensures that PostgreSQL supports UUIDs

CREATE TABLE IF NOT EXISTS TB_ROLE(
    id UUID PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS TB_USER(
    id UUID PRIMARY KEY,
    document VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS TB_USER_ROLE(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY(user_id,role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES TB_USER(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES TB_ROLE(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_ORDER(
    id UUID PRIMARY KEY,
    payment_method VARCHAR(255) NOT NULL,
    purchase_total_price DECIMAL(19, 2) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    order_status_enum VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id UUID,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES TB_USER(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_CATEGORY(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS TB_PRODUCT(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    category_id UUID,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES TB_CATEGORY(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_ORDER_PRODUCT(
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    product_total_price DECIMAL(19,2) NOT NULL,
    product_quantity INTEGER NOT NULL,
    CONSTRAINT pk_order_product PRIMARY KEY(order_id,product_id),
    CONSTRAINT fk_order_product_order FOREIGN KEY (order_id) REFERENCES TB_ORDER(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_product_product FOREIGN KEY (product_id) REFERENCES TB_PRODUCT(id) ON DELETE CASCADE
);
