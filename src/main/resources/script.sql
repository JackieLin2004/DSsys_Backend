create table db_account
(
    id            int auto_increment
        primary key,
    username      varchar(255)                           not null,
    password      varchar(255)                           not null,
    email         varchar(255)                           not null,
    role          varchar(255) default 'user'            not null,
    register_time datetime     default CURRENT_TIMESTAMP not null,
    constraint email
        unique (email),
    constraint username
        unique (username)
)
    collate = utf8mb4_general_ci;


