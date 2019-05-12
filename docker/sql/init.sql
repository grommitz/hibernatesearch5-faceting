#create database facetdb2;
create database if not exists facetdb;
#create user 'hello'@'localhost';
#grant all privileges on facetdb.* to 'hello'@'localhost' identified by 'hello';
create user 'hello2'@'%' identified by 'hello2';
grant all privileges on facetdb.* to 'hello2'@'%';