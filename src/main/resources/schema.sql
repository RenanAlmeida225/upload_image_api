CREATE TABLE IF NOT EXISTS files (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description VARCHAR(500) NOT NULL,
name VARCHAR(225) NOT NULL,
original_name VARCHAR (225) NOT NULL,
type VARCHAR(50) NOT NULL,
url VARCHAR(225) NOT NULL
);
