DROP TABLE  IF EXISTS `ticket`;
CREATE TABLE ticket
(
    id int(32) primary key auto_increment,
    name varchar(32) not null,
    phone varchar(32) not null,
    create_time datetime not null,
    question varchar(32) not null,
    state varchar(32) not null,
    images varchar(255)
);