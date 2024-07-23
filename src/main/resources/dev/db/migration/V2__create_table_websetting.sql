create table webSetting (
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    address varchar(255) not null,
    imageUrl varchar(255) not null,
    isChoose boolean default false
);