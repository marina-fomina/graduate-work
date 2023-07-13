-- liquibase formatted sql

-- changeset marina:1

INSERT INTO users (id, username, password, first_name, last_name, phone, role)
VALUES (1, 'marina@mail.ru', 'marina', 'Marina', 'Fomina', '+79264550553', 0),
       (2, 'user@gmail.com', 'password', null, null, null, 0)