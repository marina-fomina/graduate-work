-- liquibase formatted sql

-- changeset marina:1

CREATE TYPE role_type AS enum ('user', 'admin');

-- changeset marina:2

CREATE TABLE users (
                    id SERIAL PRIMARY KEY,
                    username varchar(32) UNIQUE NOT NULL,
                    password varchar(24) NOT NULL,
                    enabled INT NOT NULL,
                    first_name varchar(20),
                    last_name varchar(20),
                    phone varchar(16),
                    role role_type NOT NULL,
                    image varchar
);

-- changeset marina:3

CREATE TABLE ads (
                    id SERIAL NOT NULL PRIMARY KEY,
                    author_id INTEGER NOT NULL,
                    image varchar,
                    price INTEGER,
                    title varchar(100),
                    description varchar(1024)
);

-- changeset marina:4

CREATE TABLE comments (
                    id SERIAL NOT NULL PRIMARY KEY,
                    author_id INTEGER NOT NULL,
                    ad_id INTEGER NOT NULL,
                    text varchar NOT NULL
);

-- changeset marina:5

ALTER TABLE comments ADD FOREIGN KEY (author_id) REFERENCES users (id);

-- changeset marina:6

ALTER TABLE comments ADD FOREIGN KEY (ad_id) REFERENCES ads (id);

-- changeset marina:7

CREATE TABLE authorities (
                    username varchar NOT NULL,
                    authority varchar NOT NULL
);

-- changeset marina:8

ALTER TABLE authorities ADD FOREIGN KEY (username) REFERENCES users(username);