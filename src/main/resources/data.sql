


INSERT INTO `parking_project_directory`.`user` (username, password, enabled, first_name, last_name)
SELECT username, password, enabled, first_name, last_name FROM (SELECT 'admin' AS username, '$2a$10$DPmotpejV6QzJItREGVkces/0IQtpk5WwqGsoBnFgAV6v3CauiV8.' AS password, 1 AS enabled, 'mario' AS first_name, 'rossi' AS last_name) AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`user` WHERE username = 'admin'
) LIMIT 1;

INSERT INTO `parking_project_directory`.`user` (username, password, enabled, first_name, last_name)
SELECT username, password, enabled, first_name, last_name FROM (SELECT 'guardian' AS username, '$2a$10$3ab2iXhGVAQjPn3UDfegx.CnqtGRapsS3ILpBRHnfFvX1y8BPSyU6' AS password, 1 AS enabled, 'pinco' AS first_name, 'pallino' AS last_name) AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`user` WHERE username = 'guardian'
) LIMIT 1;

INSERT INTO `parking_project_directory`.`role` (name)
SELECT * FROM (SELECT 'ROLE_CUSTOMER') AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`role` WHERE name = 'ROLE_CUSTOMER'
) LIMIT 1;

INSERT INTO `parking_project_directory`.`role` (name)
SELECT * FROM (SELECT 'ROLE_GUARDIAN') AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`role` WHERE name = 'ROLE_GUARDIAN'
) LIMIT 1;

INSERT INTO `parking_project_directory`.`role` (name)
SELECT * FROM (SELECT 'ROLE_ADMIN') AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`role` WHERE name = 'ROLE_ADMIN'
) LIMIT 1;


INSERT INTO `parking_project_directory`.`users_roles` (user_id, role_id)
SELECT * FROM (
                  SELECT 1 AS user_id, 1 AS role_id
                  UNION ALL SELECT 1, 2
                  UNION ALL SELECT 1, 3
                  UNION ALL SELECT 2, 1
                  UNION ALL SELECT 2, 2
              ) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM `parking_project_directory`.`users_roles`
    WHERE (user_id, role_id) IN ((1, 1), (1, 2), (1, 3), (2, 1), (2, 2))
);
