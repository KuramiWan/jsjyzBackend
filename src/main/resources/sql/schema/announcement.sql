--DROP TABLE  IF EXISTS `announcement`;
CREATE TABLE announcement
(
    id int(32) primary key auto_increment,
    title varchar(32) not null,
    decription varchar(32) ,
    detail varchar(32)
);