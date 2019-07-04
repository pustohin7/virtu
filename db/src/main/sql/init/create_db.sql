\echo ===== creating database :DB_NAME and user :DB_USER =====

create database :DB_NAME encoding = 'UTF8';
create user :DB_USER with password :'DB_PASSWORD';