create table IF NOT EXISTS users11
(
    id           integer not null
        primary key,
    name         varchar(255),
    email        varchar(255),
    password     varchar(255),
    phone_number varchar(255)
);

alter table users11
    owner to postgres