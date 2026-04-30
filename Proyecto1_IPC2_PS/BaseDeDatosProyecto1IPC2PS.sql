DROP DATABASE IF EXISTS Proyecto1_PS2026;
CREATE DATABASE IF NOT EXISTS Proyecto1_PS2026;

-- SHOW VARIABLES LIKE 'validate_password%';
SET GLOBAL validate_password.policy = LOW;
SET GLOBAL validate_password.length = 4;
CREATE USER IF NOT EXISTS 'usuarioP1'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON Proyecto1_PS2026.* TO 'usuarioP1'@'localhost';
FLUSH PRIVILEGES;

-- DROP USER IF EXISTS 'usuarioP1'@'localhost';

USE Proyecto1_PS2026;

CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(30) NOT NULL
);

INSERT INTO Rol (tipo) VALUES 
("Atencion al Cliente"),
("Encargado de Operaciones"),
("Administrador");

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL UNIQUE,
    password_user VARCHAR(40) NOT NULL,
    tipo_rol INT NOT NULL,
    FOREIGN KEY (tipo_rol) REFERENCES Rol(id_rol)
);

INSERT INTO Usuario (nombre, password_user, tipo_rol) VALUES 
("Milton", "123", 3),
("Efren", "123", 2),
("Ajxup", "123", 1),    
("Ramos", "123", 1);

CREATE TABLE Destino (
    id_destino INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL UNIQUE,
    pais VARCHAR(40) NOT NULL,
    descripcion VARCHAR(150),
    clima_epoca VARCHAR(100),
    url_imagen VARCHAR(150)
);

INSERT INTO Destino (nombre, pais, descripcion, clima_epoca, url_imagen) VALUES
('Cusco', 'Perú', 'Ciudad histórica cerca de Machu Picchu', 'Templado todo el año', 
'https://ejemplo.com/cusco.jpg'),
('Cancún', 'México', 'Destino de playa famoso por sus resorts', 'Cálido en verano', 
'https://ejemplo.com/cancun.jpg');

CREATE TABLE Tipo_Servicio (
    id_tipo_servicio INT AUTO_INCREMENT PRIMARY KEY,
    servicio VARCHAR(50) NOT NULL
);

INSERT INTO Tipo_Servicio (servicio) VALUES 
('AEROLINEA'),
('HOTEL'),
('TOUR'),
('TRASLADO'),
('OTRO');

CREATE TABLE Proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) UNIQUE,
    pais VARCHAR(20),
    tipo_servicio INT NOT NULL,
    FOREIGN KEY (tipo_servicio) REFERENCES Tipo_Servicio(id_tipo_servicio)
);

INSERT INTO Proveedor (nombre, pais, tipo_servicio) VALUES
('Viajes Globales', 'Perú', 1),     -- 1
('Hotel Peru', 'Peru', 2),          -- 2
('Agencia Turistica', 'Peru', 3),   -- 3
('Transporte Peru', 'Peru', 4),     -- 4
('Aerolinea Mexico', 'México', 1),  -- 5
('Hotel Paraíso', 'México', 2),     -- 6 
('Turismo Mexico', 'México', 3),    -- 7
('Transporte Mexico', 'México', 4); -- 8

CREATE TABLE Paquete_Turistico (
    id_paquete INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    duracion_dias INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    capacidad INT NOT NULL,
    descripcion VARCHAR(150),
    estado_paquete BOOLEAN,
    destino INT NOT NULL,
    FOREIGN KEY (destino) REFERENCES Destino(id_destino) ON DELETE CASCADE
);

INSERT INTO Paquete_Turistico (nombre, duracion_dias, precio, capacidad, descripcion, 
estado_paquete, destino) VALUES
('Aventura Andes', 5, 1200.00, 20, 'Tour de montaña con guía local', 1, 1),          -- 1
('Parque Nacional', 3, 800.00, 10, 'Tour por el parque nacional', 1, 1),             -- 2
('Playa del Sol', 7, 1500.00, 15, 'Vacaciones en la playa con todo incluido', 1, 2), -- 3
('Museo Cultural', 10, 7500.00, 20, 'Conoce la el museo cultural', 1, 2);            -- 4

CREATE TABLE Servicio_Paquete (
    id_servicio_paquete INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(50),
    costo DECIMAL(10,2) NOT NULL,
    paquete INT NOT NULL,
    proveedor INT NOT NULL,
    FOREIGN KEY (paquete) REFERENCES Paquete_Turistico (id_paquete) ON DELETE CASCADE,
    FOREIGN KEY (proveedor) REFERENCES Proveedor (id_proveedor) ON DELETE CASCADE
);

INSERT INTO Servicio_Paquete (descripcion, costo, paquete, proveedor) VALUES 
('Servicio de Hotel', 250.00, 1, 2),
('Servicio de Hotel', 250.00, 2, 2),
('Servicio de Turismo', 150.00, 1, 3),
('Servicio de Turismo', 100.00, 2, 3),
('Servicio de Hotel', 300.00, 3, 6),
('Servicio de Hotel', 450.00, 4, 6),
('Servicio de Transporte', 100.00, 3, 8),
('Servicio de Transporte', 250.00, 4, 8),
('Servicio de Turismo', 300.00, 4, 7);

CREATE TABLE Cliente (
    dpi VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    fecha_nac DATE NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(30),
    nacionalidad VARCHAR(15)
);

INSERT INTO Cliente (dpi, nombre, fecha_nac, telefono, email, nacionalidad) VALUES
('1234567890123', 'Carlos Pérez', '1990-05-12', '5551234567', 'carlos.perez@mail.com', 'Peruana'),
('9876543210987', 'Ana López', '1985-11-23', '5559876543', 'ana.lopez@mail.com', 'Mexicana');

CREATE TABLE Estado_Reservacion (
    id_estado_reservacion INT AUTO_INCREMENT PRIMARY KEY,
    tipo_estado VARCHAR(20) NOT NULL
);

INSERT INTO Estado_Reservacion (tipo_estado) VALUES 
('PENDIENTE'),
('CONFIRMADA'),
('CANCELADA'),
('COMPLETADA');

CREATE TABLE Reservacion (
    numero_reservacion INT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_viaje DATE NOT NULL,
    cantidad_pasajeros INT NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    agente_de_registro INT NOT NULL,
    paquete INT NOT NULL,
    estado INT NOT NULL, -- Se debe actualizar
    FOREIGN KEY(agente_de_registro) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY(paquete) REFERENCES Paquete_Turistico(id_paquete) ON DELETE CASCADE,
    FOREIGN KEY (estado) REFERENCES Estado_Reservacion(id_estado_reservacion)
);

CREATE TABLE Cancelacion_Reservacion (
    id_cancelacion INT AUTO_INCREMENT PRIMARY KEY,
    id_reservacion INT NOT NULL,
    porcentaje_reembolso INT NOT NULL,
    fecha_cancelacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservacion) REFERENCES Reservacion(numero_reservacion) ON DELETE CASCADE
);

CREATE TABLE Pasajeros_Reservacion (
    id_reservacion INT NOT NULL,
    id_pasajero VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_reservacion) REFERENCES Reservacion(numero_reservacion) ON DELETE CASCADE,
    FOREIGN KEY (id_pasajero) REFERENCES Cliente(dpi) ON DELETE CASCADE,
    PRIMARY KEY (id_reservacion, id_pasajero)
);

CREATE TABLE Metodo_Pago (
    id_metodo_pago INT AUTO_INCREMENT PRIMARY KEY,
    metodo VARCHAR(15) NOT NULL
);

INSERT INTO Metodo_Pago (metodo) VALUES 
('EFECTIVO'),
('TARJETA'),
('TRANSFERENCIA');

CREATE TABLE Pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    monto_pagado DECIMAL(10,2) NOT NULL,
    fecha DATE DEFAULT (CURRENT_DATE),
    num_reservacion INT NOT NULL,
    metodo INT NOT NULL,
    FOREIGN KEY (num_reservacion) REFERENCES Reservacion(numero_reservacion),
    FOREIGN KEY (metodo) REFERENCES Metodo_Pago(id_metodo_pago)
);
