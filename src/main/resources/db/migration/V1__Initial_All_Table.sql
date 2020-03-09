DROP TABLE IF EXISTS sushi;

CREATE TABLE sushi (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30),
time_to_make INT DEFAULT NULL
);

DROP TABLE IF EXISTS sushi_order;

CREATE TABLE sushi_order (
id INT AUTO_INCREMENT PRIMARY KEY,
status_id INT NOT NULL,
sushi_id INT NOT NULL,
createdAt TIMESTAMP NOT NULL default CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS status;

CREATE TABLE status (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL
);

INSERT INTO sushi (name, time_to_make) VALUES
('California Roll', 30),
('Kamikaze Roll', 40),
('Dragon Eye', 50);

INSERT INTO status (name) VALUES
('created'),
('in-progress'),
('paused'),
('finished'),
('cancelled');