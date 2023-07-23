-- liquibase formatted sql

-- changeset marina:1

INSERT INTO users (username, password, enabled, first_name, last_name, phone, role)
VALUES ('marina@mail.ru', 'marina',  true, 'Marina', 'Fomina', '+79264550553', 'user'),
       ('user@gmail.com', 'password', true, null, null, null, 'user'),
       ('admin', 'admin123', true, null, null, null, 'admin')