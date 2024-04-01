-- TODO Task 5

drop database if exists pizza_factory;
create database pizza_factory;
use pizza_factory;

create table orders (
	order_id char(8) not null,
    name varchar(64),    
    email varchar(64),
    pizza_id varchar(128),
    
    primary key(order_id)
);