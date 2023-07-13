-- liquibase formatted sql

-- changeset marina:1

CREATE TABLE users (
                    id INTEGER PRIMARY KEY,
                    username varchar(32) NOT NULL,
                    password varchar(24) NOT NULL,
                    first_name varchar(20),
                    last_name varchar(20),
                    phone varchar(16),
                    role INTEGER NOT NULL,
                    image varchar
);

-- changeset marina:2

CREATE TABLE ads (
                    id INTEGER NOT NULL PRIMARY KEY,
                    author_id INTEGER NOT NULL,
                    image varchar,
                    price INTEGER,
                    title varchar(100),
                    description varchar(1024)
);

-- changeset marina:3

CREATE TABLE comments (
                    id INTEGER NOT NULL PRIMARY KEY,
                    author_id INTEGER NOT NULL,
                    ad_id INTEGER NOT NULL,
                    text varchar NOT NULL
);

-- changeset marina:4

ALTER TABLE comments ADD FOREIGN KEY (author_id) REFERENCES users (id);

-- changeset marina:5

ALTER TABLE comments ADD FOREIGN KEY (ad_id) REFERENCES ads (id);