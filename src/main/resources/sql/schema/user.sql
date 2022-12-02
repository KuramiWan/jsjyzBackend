DROP TABLE  IF EXISTS `ticket`;
CREATE TABLE user
(
    id int(32) primary key auto_increment,
    username varchar(30) not null;
    password varchar(255) not null;
);