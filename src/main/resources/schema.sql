DROP SCHEMA IF EXISTS `parking_project_directory`;
CREATE SCHEMA `parking_project_directory`;

use `parking_project_directory`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `parking_area` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name` varchar(40) DEFAULT NULL,
                             `matrix_order` int DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `parking_spot` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `status` ENUM('FREE', 'BUSY', 'RESERVED', 'UNAVAILABLE'),
                              `stripe_color` ENUM('WHITE', 'YELLOW', 'PINK'),
                              `row_index` int DEFAULT NULL,
                              `column_index` int DEFAULT NULL,
                              `parking_area_id` int DEFAULT NULL,
                              PRIMARY KEY (`id`),

                              KEY `FK_PARKING_AREA_idx` (`parking_area_id`),

                              CONSTRAINT `FK_PARKING_AREA`
                              FOREIGN KEY (`parking_area_id`)
                              REFERENCES `parking_area` (`id`)
                              ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `parking` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `estimated_time` time DEFAULT NULL,
                            `arrival` datetime DEFAULT NULL,
                            `leaving` datetime DEFAULT NULL,
                            `parking_spot_id` int DEFAULT NULL,
                            `payment_receipt_id` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK_PAYMENT_RECEIPT_idx` (`payment_receipt_id`),
                            KEY `FK_PARKING_SPOT_idx` (`parking_spot_id`),
                            CONSTRAINT `FK_PARKING_SPOT`
                                FOREIGN KEY (`parking_spot_id`)
                                    REFERENCES `parking_spot` (`id`)
                                    ON DELETE NO ACTION ON UPDATE NO ACTION,
                            CONSTRAINT `FK_PAYMENT_RECEIPT` FOREIGN KEY (`payment_receipt_id`) REFERENCES `payment_receipt` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `payment_receipt` (
                                                 `id` int NOT NULL AUTO_INCREMENT,
                                                 `timestamp` datetime DEFAULT NULL,
                                                 `amount` double DEFAULT NULL,
                                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `booking` (
                                              `id` int NOT NULL AUTO_INCREMENT,
                                              `date` date DEFAULT NULL,
                                              `payment_receipt_id` int DEFAULT NULL,
                                              PRIMARY KEY (`id`),
                                              FOREIGN KEY (`payment_receipt_id`)
                                              REFERENCES `payment_receipt` (`id`)
                                              ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



SET FOREIGN_KEY_CHECKS = 1;