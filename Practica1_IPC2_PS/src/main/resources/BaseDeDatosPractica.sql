CREATE DATABASE Practica1_202330209;

USE Practica1_202330209;

CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(20)
);

CREATE TABLE Sucursal(
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(75) UNIQUE
);

CREATE TABLE Usuario(
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL UNIQUE,
    clave_ingreso VARCHAR(20) NOT NULL,
    id_rol INT NOT NULL,
    sucursal INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol),
    FOREIGN KEY (sucursal) REFERENCES Sucursal(id_sucursal)
);

CREATE TABLE Producto(
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE
);

CREATE TABLE Sucursal_Producto(
    id_sucursal INT NOT NULL,
    id_producto INT NOT NULL,
    FOREIGN KEY (id_sucursal) REFERENCES Sucursal(id_sucursal),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto),
    PRIMARY KEY (id_sucursal, id_producto)
);

CREATE TABLE Partida(
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    usuario INT NOT NULL,
    sucursal INT NOT NULL,
    puntaje INT NOT NULL,
    nivel_alcanzado INT NOT NULL,
    FOREIGN KEY(usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (sucursal) REFERENCES Sucursal(id_sucursal)
);

CREATE TABLE Estado(
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    tipo_estado VARCHAR(20)
);

CREATE TABLE Pedido(
    numero_pedido INT AUTO_INCREMENT PRIMARY KEY,
    producto INT NOT NULL,
    estado INT,
    partida INT NOT NULL,
    FOREIGN KEY (producto) REFERENCES Producto (id_producto),
    FOREIGN KEY (estado) REFERENCES Estado (id_estado),
    FOREIGN KEY (partida) REFERENCES Partida (id_partida)
);

CREATE TABLE Historial_Pedido(
    id_pedido INT NOT NULL,
    id_estado INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(numero_pedido),
    FOREIGN KEY (id_estado) REFERENCES Estado(id_estado),
    PRIMARY KEY (id_pedido, id_estado)
);

INSERT INTO Rol (nombre_rol) VALUES 
("Super Administrador"),
("Administrador"),
("Usuario");

INSERT INTO Sucursal (nombre) VALUES ("La capital");

INSERT INTO Usuario (nombre, clave_ingreso, id_rol, sucursal) VALUES 
("super admin", "super", 1, 1),
("admin", "123", 2, 1),
("jugador", "123", 3, 1);

INSERT INTO Producto (nombre) VALUES 
("Pizza de Jamon"),
("Pizza de Peperoni"),
("Pizza de Hawainana"),
("Pizza de Queso"),
("Pizza de Pollo"),
("Pizza de Vegetariana");

INSERT INTO Sucursal_Producto (id_sucursal, id_producto) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6);

INSERT INTO Estado (tipo_estado) VALUES 
("RECIBIDO"),
("PREPARANDO"),
("EN_HORNO"),
("ENTREGADO"), 
("RECHAZADO"), 
("NO_ENTREGADO");

