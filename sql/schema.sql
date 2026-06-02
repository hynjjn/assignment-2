-- Web Programming Assignment 2 : Favorite Fruit Survey
-- MySQL / MariaDB schema + initial data
--
-- Run with:  mysql -u root -p < sql/schema.sql

CREATE DATABASE IF NOT EXISTS survey
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE survey;

DROP TABLE IF EXISTS fruit;

CREATE TABLE fruit (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50)  NOT NULL UNIQUE,
    votes INT          NOT NULL DEFAULT 0
);

-- Initial data: each fruit starts with 1 vote.
-- 1 / 4 = 25% each, as required by the assignment.
INSERT INTO fruit (name, votes) VALUES
    ('Apple',      1),
    ('Grape',      1),
    ('Strawberry', 1),
    ('Melon',      1);
