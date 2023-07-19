-- liquibase formatted sql

-- changeset marina:1

INSERT INTO users (username, password, enabled, first_name, last_name, phone, role)
VALUES ('marina@mail.ru', 'marina', 1, 'Marina', 'Fomina', '+79264550553', 'user'),
       ('user@gmail.com', 'password', 1, null, null, null, 'user'),
       ('admin', 'admin123', 1, null, null, null, 'admin')