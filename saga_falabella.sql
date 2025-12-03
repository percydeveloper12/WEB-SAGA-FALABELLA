CREATE DATABASE saga_falabella;
USE saga_falabella;

CREATE TABLE cargo (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(40) NOT NULL
);

INSERT INTO cargo (name) VALUES 
('Postulante'),
('Personal RRHH'),
('Jefe RRHH'),
('Analista de Selección');


CREATE TABLE usuario (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(40) NOT NULL,
   cargo_id INT,
   FOREIGN KEY (cargo_id) REFERENCES cargo(id)
);


CREATE TABLE perfil_postulante (
   id INT AUTO_INCREMENT PRIMARY KEY,
   usuario_id INT,
   nombre VARCHAR(40),
   apellido VARCHAR(40),
   dni VARCHAR(20),
   cv VARCHAR(100),
   FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);


CREATE TABLE area (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(40) NOT NULL
);

INSERT INTO area (name) VALUES 
('Área de Sistemas'),
('Área de Recursos Humanos'),
('Área de Marketing'),
('Área de Logística');


CREATE TABLE puestos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(40) NOT NULL
);

INSERT INTO puestos (name) VALUES 
('Dev Junior'),
('Diseñador'),
('Jefe de Sistemas'),
('Marketing Junior'),
('Logística');

CREATE TABLE estados_requerimiento (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(20) NOT NULL
);

INSERT INTO estados_requerimiento (nombre) VALUES 
('Pendiente'),
('Aprobado'),
('Rechazado');


CREATE TABLE estados_requerimiento_final (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(20) NOT NULL
);

INSERT INTO estados_requerimiento_final (nombre) VALUES 
('Contratado'),
('No Contratado');


CREATE TABLE requerimiento_puestos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   puestos_id INT,
   fecha DATETIME,
   descripcion VARCHAR(100),
   area_id INT,
   fecha_expi DATETIME,
   estados_requerimiento_id INT,
   FOREIGN KEY (puestos_id) REFERENCES puestos(id),
   FOREIGN KEY (area_id) REFERENCES area(id),
   FOREIGN KEY (estados_requerimiento_id) REFERENCES estados_requerimiento(id)
);


CREATE TABLE requerimiento_puestos_postulantes (
   id INT AUTO_INCREMENT PRIMARY KEY,
   requerimiento_puestos_id INT,
   usuario_id INT,
   estados_requerimiento_final_id INT,
   FOREIGN KEY (requerimiento_puestos_id) REFERENCES requerimiento_puestos(id),
   FOREIGN KEY (usuario_id) REFERENCES usuario(id),
   FOREIGN KEY (estados_requerimiento_final_id) REFERENCES estados_requerimiento_final(id)
);

SELECT * FROM usuario;
