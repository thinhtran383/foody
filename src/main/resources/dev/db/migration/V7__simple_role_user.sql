drop table userRole;

alter table users
add column roleId int;

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (roleId) REFERENCES roles (roleId);