-- Tạo các bảng
CREATE TABLE category (
                          categoryId INT AUTO_INCREMENT PRIMARY KEY,
                          categoryName VARCHAR(255) NULL
);



CREATE TABLE menuItems (
                           itemId INT AUTO_INCREMENT PRIMARY KEY,
                           categoryId INT NULL,
                           itemName VARCHAR(255) NULL,
                           image VARCHAR(255) NULL,
                           price DECIMAL(10, 2) NULL,
                           CONSTRAINT menuitems_ibfk_1 FOREIGN KEY (categoryId) REFERENCES category (categoryId)
);

CREATE TABLE roles (
                       roleId INT AUTO_INCREMENT PRIMARY KEY,
                       roleName VARCHAR(255) NULL
);

CREATE TABLE tables (
                        tableId INT AUTO_INCREMENT PRIMARY KEY,
                        status ENUM('FREE', 'OCCUPIED') DEFAULT 'FREE' NULL
);

CREATE TABLE orders (
                        orderId INT NOT NULL PRIMARY KEY,
                        tableId INT NULL,
                        createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
                        CONSTRAINT orders_ibfk_1 FOREIGN KEY (tableId) REFERENCES tables (tableId)
);

CREATE TABLE orderDetails (
                              orderDetailId INT AUTO_INCREMENT PRIMARY KEY,
                              orderId INT NULL,
                              itemId INT NULL,
                              quantity INT NULL,
                              CONSTRAINT orderdetails_ibfk_1 FOREIGN KEY (orderId) REFERENCES orders (orderId),
                              CONSTRAINT orderdetails_ibfk_2 FOREIGN KEY (itemId) REFERENCES menuItems (itemId)
);

CREATE TABLE users (
                       userId INT AUTO_INCREMENT PRIMARY KEY,
                       userName VARCHAR(255) NULL,
                       password VARCHAR(255) NULL,
                       CONSTRAINT unique_userName UNIQUE (userName)
);


CREATE TABLE userInfo (
                          userId INT NOT NULL PRIMARY KEY,
                          name VARCHAR(255) NULL,
                          email VARCHAR(255) NULL,
                          phone VARCHAR(255) NULL,
                          address VARCHAR(255) NULL,
                          CONSTRAINT userinfo_ibfk_1 FOREIGN KEY (userId) REFERENCES users (userId)
);

CREATE TABLE userRole (
                          userId INT NOT NULL,
                          roleId INT NOT NULL,
                          PRIMARY KEY (userId, roleId),
                          CONSTRAINT userrole_ibfk_1 FOREIGN KEY (userId) REFERENCES users (userId),
                          CONSTRAINT userrole_ibfk_2 FOREIGN KEY (roleId) REFERENCES roles (roleId)
);


-- Tạo các chỉ mục
CREATE INDEX categoryId ON menuItems (categoryId);
CREATE INDEX orderId ON orderDetails (orderId);
CREATE INDEX tableId ON orders (tableId);
CREATE INDEX roleId ON userRole (roleId);
