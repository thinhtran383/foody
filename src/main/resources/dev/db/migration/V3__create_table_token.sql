create table token
(
    id             serial primary key,
    token          varchar(255) not null,
    expirationDate timestamp    not null,
    revoked        boolean      not null,
    expired        boolean      not null,
    userId         int          not null,
    foreign key (userId) references users (userId)
);