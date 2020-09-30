drop table if exists APP_USERS cascade;
drop table if exists ADDRESSES cascade;

create table ADDRESSES
(
   id     varchar(42)  primary key,
   street varchar(100) not null,
   city   varchar(100) not null
);

create table APP_USERS
(
   id      varchar(42) primary key,
   address varchar(42)  not null references ADDRESSES (id),
   name    varchar(100) not null,
   email   varchar(100) not null,
   enabled boolean not null default true
);