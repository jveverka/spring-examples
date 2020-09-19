drop table if exists APP_USERS cascade;

create table APP_USERS
(
   id     varchar(42) primary key,
   name   varchar(100) not null,
   email  varchar(100) not null
);
