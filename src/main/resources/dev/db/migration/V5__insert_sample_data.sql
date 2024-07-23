-- Insert sample data into category table
INSERT INTO category (categoryName)
VALUES ('Appetizers'),
       ('Main Courses'),
       ('Desserts'),
       ('Beverages'),
       ('Vegetarian');

-- Insert sample data into menuItems table
-- Appetizers
INSERT INTO menuItems (categoryId, itemName, image, price)
VALUES ((SELECT categoryId FROM category WHERE categoryName = 'Appetizers'), 'Spring Rolls', 'spring_rolls.jpg', 5.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Appetizers'), 'Summer Rolls', 'summer_rolls.jpg', 6.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Appetizers'), 'Prawn Crackers', 'prawn_crackers.jpg',
        4.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Appetizers'), 'Grilled Pork Skewers',
        'grilled_pork_skewers.jpg', 7.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Appetizers'), 'Papaya Salad', 'papaya_salad.jpg', 5.00);

-- Main Courses
INSERT INTO menuItems (categoryId, itemName, image, price)
VALUES ((SELECT categoryId FROM category WHERE categoryName = 'Main Courses'), 'Pho', 'pho.jpg', 9.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Main Courses'), 'Banh Mi', 'banh_mi.jpg', 6.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Main Courses'), 'Bun Cha', 'bun_cha.jpg', 8.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Main Courses'), 'Com Tam', 'com_tam.jpg', 7.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Main Courses'), 'Banh Xeo', 'banh_xeo.jpg', 8.00);

-- Desserts
INSERT INTO menuItems (categoryId, itemName, image, price)
VALUES ((SELECT categoryId FROM category WHERE categoryName = 'Desserts'), 'Che Ba Mau', 'che_ba_mau.jpg', 4.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Desserts'), 'Banh Flan', 'banh_flan.jpg', 3.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Desserts'), 'Che Troi Nuoc', 'che_troi_nuoc.jpg', 4.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Desserts'), 'Banh Chuoi', 'banh_chuoi.jpg', 3.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Desserts'), 'Kem Xoi', 'kem_xoi.jpg', 4.00);

-- Beverages
INSERT INTO menuItems (categoryId, itemName, image, price)
VALUES ((SELECT categoryId FROM category WHERE categoryName = 'Beverages'), 'Ca Phe Sua Da', 'ca_phe_sua_da.jpg', 3.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Beverages'), 'Nuoc Mia', 'nuoc_mia.jpg', 2.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Beverages'), 'Sinh To Bo', 'sinh_to_bo.jpg', 4.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Beverages'), 'Tra Da', 'tra_da.jpg', 1.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Beverages'), 'Nuoc Chanh', 'nuoc_chanh.jpg', 2.00);

-- Vegetarian
INSERT INTO menuItems (categoryId, itemName, image, price)
VALUES ((SELECT categoryId FROM category WHERE categoryName = 'Vegetarian'), 'Vegetarian Spring Rolls',
        'vegetarian_spring_rolls.jpg', 5.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Vegetarian'), 'Vegetarian Pho', 'vegetarian_pho.jpg',
        9.00),
       ((SELECT categoryId FROM category WHERE categoryName = 'Vegetarian'), 'Tofu Banh Mi', 'tofu_banh_mi.jpg', 6.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Vegetarian'), 'Vegetarian Bun Cha',
        'vegetarian_bun_cha.jpg', 8.50),
       ((SELECT categoryId FROM category WHERE categoryName = 'Vegetarian'), 'Vegetarian Banh Xeo',
        'vegetarian_banh_xeo.jpg', 8.00);

update menuItems set price = 1000;

update menuItems set image='https://image.tienphong.vn/w890/Uploaded/2024/rkznae/2021_12_03/photo-1-16158774522971708629774-5166.jpg';

insert into tables (status)
values ('FREE');
insert into tables (status)
values ('FREE');
insert into tables (status)
values ('FREE');
insert into tables (status)
values ('FREE');
insert into tables (status)
values ('FREE');



insert into roles(roleName) value ('ROLE_USER');
insert into roles(roleName) value ('ROLE_ADMIN');

insert into users(userName, password) value ('admin','$2a$10$iwzlUHstN.iuMTOt45lsVeYb9noTcDuGcWeKV7mex5KSE/fjElwFS');
insert into userInfo(userId, name, email, phone, address) value (1, 'admin','admin@admin.com','0947445420','Ha Noi');

insert into userRole(userId, roleId) value (1, 2);
