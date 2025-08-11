CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO TB_ROLE(id,role_name) VALUES
('11111111-1111-1111-1111-111111111111','ADMIN'),
('22222222-2222-2222-2222-222222222222','MANAGER'),
('33333333-3333-3333-3333-333333333333','CUSTOMER');

INSERT INTO TB_USER(id,document,username,password,full_name,email) VALUES
('44444444-4444-4444-4444-444444444444','111','marcos','$2a$10$RWhww/q2QyddUc9/YaZh8erjc82wx0qoVi8TeOYiIwdYDVuQ.VW8i','marcos soto','ADMIN_m@gmail.com'),
('55555555-5555-5555-5555-555555555555','222','pedro','$2a$10$RWhww/q2QyddUc9/YaZh8erjc82wx0qoVi8TeOYiIwdYDVuQ.VW8i','pedro diaz','MANAGER_p@gmail.com'),
('66666666-6666-6666-6666-666666666666','333','lina','$2a$10$RWhww/q2QyddUc9/YaZh8erjc82wx0qoVi8TeOYiIwdYDVuQ.VW8i','lina diaz','CUSTOMER_l@gmail.com');

INSERT INTO TB_USER_ROLE(user_id,role_id) VALUES
-- marcos -> ADMIN
('44444444-4444-4444-4444-444444444444','11111111-1111-1111-1111-111111111111'),
-- pedro -> MANAGER
('55555555-5555-5555-5555-555555555555','22222222-2222-2222-2222-222222222222'),
-- lina -> CUSTOMER
('66666666-6666-6666-6666-666666666666','33333333-3333-3333-3333-333333333333');

INSERT INTO TB_CATEGORY(id,name,description,image_url) VALUES
('77777777-7777-7777-7777-777777777777','Tech','description 1...','img_url'),
('88888888-8888-8888-8888-888888888888','Sports','description 1...','img_url');

INSERT INTO TB_PRODUCT(id,name,description,image_url,price,stock_quantity,category_id) VALUES
('99999999-9999-9999-9999-999999999999','tv z','description 1...','img_url',100.00,2,'77777777-7777-7777-7777-777777777777'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa','pc z','description 1...','img_url',50.00,2,'77777777-7777-7777-7777-777777777777'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb','ball','description 1...','img_url',10.00,2,'88888888-8888-8888-8888-888888888888');