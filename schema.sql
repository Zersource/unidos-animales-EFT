-- Unidos por los Animales - Schema SQL
CREATE DATABASE IF NOT EXISTS unidos_animales CHARACTER SET utf8mb4;
USE unidos_animales;
CREATE TABLE IF NOT EXISTS mascotas (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100) NOT NULL, especie VARCHAR(50) NOT NULL, raza VARCHAR(100), edad INT, descripcion TEXT, estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE', foto_url VARCHAR(255));
CREATE TABLE IF NOT EXISTS adoptantes (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL, telefono VARCHAR(20), direccion VARCHAR(200), motivacion TEXT);
CREATE TABLE IF NOT EXISTS solicitudes_adopcion (id BIGINT AUTO_INCREMENT PRIMARY KEY, fecha DATE NOT NULL, estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE', mensaje TEXT, coordinador_username VARCHAR(50), mascota_id BIGINT NOT NULL, adoptante_id BIGINT NOT NULL, FOREIGN KEY (mascota_id) REFERENCES mascotas(id), FOREIGN KEY (adoptante_id) REFERENCES adoptantes(id));
INSERT INTO mascotas (nombre, especie, raza, edad, descripcion, estado) VALUES ("Firulais","Perro","Mestizo",3,"Perro amigable","DISPONIBLE"),("Luna","Gato","Siames",2,"Gata tranquila","DISPONIBLE"),("Rocky","Perro","Labrador",5,"Perro adulto leal","DISPONIBLE"),("Michi","Gato","Mestizo",1,"Gatito joven","ADOPTADO");
INSERT INTO adoptantes (nombre, email, telefono, direccion, motivacion) VALUES ("Maria Gonzalez","maria@email.com","+56912345678","Vina del Mar","Tengo patio grande"),("Carlos Perez","carlos@email.com","+56987654321","Valparaiso","Mi familia quiere una mascota");
INSERT INTO solicitudes_adopcion (fecha, estado, mensaje, mascota_id, adoptante_id) VALUES (CURDATE(),"PENDIENTE","Quiero adoptar a Firulais",1,1),(CURDATE(),"PENDIENTE","Me interesa Luna",2,2);
