-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema chatop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema chatop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `chatop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `chatop` ;

-- -----------------------------------------------------
-- Table `chatop`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatop`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `USERS_index` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `chatop`.`rentals`
-- -----------------------------------------------------
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
  INDEX `owner_id` (`owner_id` ASC) VISIBLE,
  CONSTRAINT `rentals_ibfk_1`
    FOREIGN KEY (`owner_id`)
    REFERENCES `chatop`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `chatop`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatop`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rental_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `message` VARCHAR(2000) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `rental_id` (`rental_id` ASC) VISIBLE,
  CONSTRAINT `messages_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `chatop`.`users` (`id`),
  CONSTRAINT `messages_ibfk_2`
    FOREIGN KEY (`rental_id`)
    REFERENCES `chatop`.`rentals` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
