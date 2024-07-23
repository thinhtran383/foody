drop table userInfo;

alter table users
    add column fullname    varchar(255),
    add column email   varchar(255),
    add column phoneNumber   varchar(11),
    add column address varchar(255);
