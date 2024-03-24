USE Library;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    gmail VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS reports;

CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    report_date DATE NOT NULL,
    status INT NOT NULL
);

DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    ISBN VARCHAR(20) NOT NULL UNIQUE,
    genre VARCHAR(100) NOT NULL,
    dueDate DATE,
    owner BIGINT
);
