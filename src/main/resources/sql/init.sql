
CREATE SCHEMA IF NOT EXISTS `chatop`;
USE `chatop`;


CREATE TABLE IF NOT EXISTS `chatop`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `USERS_index` (`email`));


CREATE TABLE IF NOT EXISTS `chatop`.`rentals` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `surface` DECIMAL(10,0) NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NULL DEFAULT NULL,
  `picture` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(2000) NULL DEFAULT NULL,
  `owner_id` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `owner_id` (`owner_id`),
  CONSTRAINT `rentals_ibfk_1`
    FOREIGN KEY (`owner_id`)
    REFERENCES `chatop`.`users` (`id`));


CREATE TABLE IF NOT EXISTS `chatop`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rental_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `message` VARCHAR(2000) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id`),
  INDEX `rental_id` (`rental_id`),
  CONSTRAINT `messages_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `chatop`.`users` (`id`),
  CONSTRAINT `messages_ibfk_2`
    FOREIGN KEY (`rental_id`)
    REFERENCES `chatop`.`rentals` (`id`));


