CREATE DATABASE energy_tracker;
USE energy_tracker;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    house_square_footage INT
);

CREATE TABLE appliances (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100),
    power_consumption_per_hour DOUBLE,
    hours_used_per_month INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
