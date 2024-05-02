# DROP SCHEMA IF EXISTS `parking_project_directory`;
CREATE SCHEMA IF NOT EXISTS `parking_project_directory`;

use `parking_project_directory`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `payment_method` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `pan` varchar(40) DEFAULT NULL,
                                         `cvv` varchar(40) DEFAULT NULL,
                                         `expiration_date` date DEFAULT NULL,
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(50) NOT NULL,
                        `password` char(80) NOT NULL,
                        `enabled` tinyint NOT NULL,
                        `first_name` varchar(64) NOT NULL,
                        `last_name` varchar(64) NOT NULL,
                        `license_plate` varchar(7) DEFAULT NULL,
                        `is_disabled` boolean DEFAULT FALSE,
                        `is_pregnant` boolean DEFAULT FALSE,
                        `payment_method_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),

                        FOREIGN KEY (`payment_method_id`)
                        REFERENCES `payment_method` (`id`)
                        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

# CREATE TABLE IF NOT EXISTS `account` (
#                                               `id` int NOT NULL AUTO_INCREMENT,
#                                               `username` varchar(40) DEFAULT NULL,
#                                               `password` varchar(100) DEFAULT NULL,
#                                               `enabled` tinyint NOT NULL,
#                                               `first_name` varchar(40) DEFAULT NULL,
#                                               `last_name` varchar(40) DEFAULT NULL,
#                                               `license_plate` varchar(7) DEFAULT NULL,
#                                               `is_disabled` boolean DEFAULT FALSE,
#                                               `is_pregnant` boolean DEFAULT FALSE,
#                                               `payment_method_id` int DEFAULT NULL,
#                                               PRIMARY KEY (`id`),
#
#                                               FOREIGN KEY (`payment_method_id`)
#                                                   REFERENCES `payment_method` (`id`)
#                                                   ON DELETE NO ACTION ON UPDATE NO ACTION
# ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


# CREATE TABLE IF NOT EXISTS `roles` (
#                          `id` int NOT NULL AUTO_INCREMENT,
#                          `role` varchar(50) NOT NULL,
#                          `account_id` int NOT NULL,
#                          PRIMARY KEY (`id`),
#                          UNIQUE KEY `AUTHORITIES_idx` (`account_id`,`role`),
#                          CONSTRAINT `AUTHORITIES` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `role` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `users_roles` (
                               `user_id` int(11) NOT NULL,
                               `role_id` int(11) NOT NULL,

                               PRIMARY KEY (`user_id`,`role_id`),

                               KEY `FK_ROLE_idx` (`role_id`),

                               CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`)
                                   REFERENCES `user` (`id`)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION,

                               CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`)
                                   REFERENCES `role` (`id`)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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
                            `account_id` int DEFAULT NULL,

                            PRIMARY KEY (`id`),

                            KEY `FK_PAYMENT_RECEIPT_idx` (`payment_receipt_id`),
                            KEY `FK_PARKING_SPOT_idx` (`parking_spot_id`),
                            KEY `FK_ACCOUNT_idx` (`account_id`),

                            CONSTRAINT `FK_PARKING_SPOT`
                                FOREIGN KEY (`parking_spot_id`)
                                    REFERENCES `parking_spot` (`id`)
                                    ON DELETE NO ACTION ON UPDATE NO ACTION,

                            CONSTRAINT `FK_PAYMENT_RECEIPT` FOREIGN KEY (`payment_receipt_id`)
                                REFERENCES `payment_receipt` (`id`)
                                ON DELETE NO ACTION ON UPDATE NO ACTION,

                            CONSTRAINT `FK_ACCOUNT`
                                FOREIGN KEY (`account_id`)
                                    REFERENCES `user` (`id`)
                                    ON DELETE NO ACTION ON UPDATE NO ACTION
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
                                              `account_id` int DEFAULT NULL,

                                              PRIMARY KEY (`id`),

                                              FOREIGN KEY (`payment_receipt_id`)
                                              REFERENCES `payment_receipt` (`id`)
                                              ON DELETE NO ACTION ON UPDATE NO ACTION,


                                                  FOREIGN KEY (`account_id`)
                                                  REFERENCES `user` (`id`)
                                                  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



SET FOREIGN_KEY_CHECKS = 1;
