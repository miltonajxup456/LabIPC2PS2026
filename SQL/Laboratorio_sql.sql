CREATE DATABASE Ejercicio_Laboratorio_sql

CREATE TABLE Sucursales(
    codigo_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    ubicacion VARCHAR(75)
);

CREATE TABLE Empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empleado VARCHAR(50),
    cargo VARCHAR(50),
    sucursal INT NOT NULL,
    FOREIGN KEY (sucursal) REFERENCES Sucursales(codigo_sucursal)
);

CREATE TABLE Productos (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(50),
    descripcion VARCHAR(150),
    precio INT,
    cantidad_en_stock INT
);

CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proveedor VARCHAR(50),
    direccion VARCHAR(100),
    contacto VARCHAR(40)
);

CREATE TABLE Productos_Proveedores (
    producto_id INT NOT NULL,
    proveedor_id INT NOT NULL,
    PRIMARY KEY (producto_id, proveedor_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(codigo),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    direccion VARCHAR(100),
    tipo_cliente VARCHAR(20)
);

CREATE TABLE Pedidos (
    numero_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_compra DATE,
    id_empleado INT NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_empleado) REFERENCES Empleados(id_empleado),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Productos_Pedido (
    numero_pedido INT NOT NULL,
    codigo_producto INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (numero_pedido, codigo_producto),
    FOREIGN KEY (numero_pedido) REFERENCES Pedidos(numero_pedido),
    FOREIGN KEY (codigo_producto) REFERENCES Productos(codigo)
);

CREATE TABLE Facturas (
    numero_factura INT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido INT NOT NULL,
    id_cliente INT NOT NULL,
    fecha_emision DATE,
    monto_total INT,
    estado_pago VARCHAR(20),
    id_empleado INT NOT NULL,
    FOREIGN KEY (numero_pedido) REFERENCES Pedidos(numero_pedido),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_empleado) REFERENCES Empleados(id_empleado)
);

--Valores para sucursarles
INSERT INTO Sucursales(ubicacion)
VALUES ("Totonicapan"),
("Quetzaltenango"),
("San Marcos");

--Valores para Empleados
INSERT INTO Empleados(nombre_empleado, cargo, sucursal)
VALUES ('Kevin Ajxup', 'Gerente', 1),
("Carlos Perez", "Vendedor", 1),
("Ana Lopez", "Facturacion", 2),
("Luis Garcia", "Vendedor", 2),
("Wilmer Morales", "Gerente", 3);

INSERT INTO Clientes (nombre, direccion, tipo_cliente) VALUES
('Tech Solutions SA', 'Av. Reforma 123', 'corporativo'),
('Juan Martínez', 'Calle Hidalgo 45', 'individual'),
('Innovatech', 'Blvd. Central 678', 'corporativo'),
('Laura Gómez', 'Col. Centro 890', 'individual'),
('Digital Corp', 'Av. Norte 456', 'corporativo');

INSERT INTO Productos (nombre_producto, descripcion, precio, cantidad_en_stock) VALUES
('Laptop Dell', 'Laptop 16GB RAM', 15000, 20),
('Mouse Logitech', 'Mouse inalámbrico', 500, 100),
('Teclado Mecánico', 'Teclado RGB', 1200, 50),
('Monitor Samsung', 'Monitor 24 pulgadas', 4000, 30),
('Impresora HP', 'Impresora multifuncional', 3500, 15);

INSERT INTO Proveedores (nombre_proveedor, direccion, contacto) VALUES
('TechDistribuciones', 'Av. Industrial 100', 'tech@proveedor.com'),
('Global Electronics', 'Zona Norte 200', 'ventas@global.com'),
('Suministros Digitales', 'Parque Logístico 300', 'contacto@suministros.com');

INSERT INTO Productos_Proveedores (producto_id, proveedor_id) VALUES
(1,1),(2,1),(3,1),
(4,2),(5,2),(6,2),
(7,3),(8,3),(9,3),(10,1);

INSERT INTO Pedidos (fecha_compra, id_cliente, id_empleado) VALUES
('2026-01-10',1,2),
('2026-01-11',2,4),
('2026-01-12',3,5),
('2026-01-13',4,3),
('2026-01-14',5,1);

INSERT INTO Productos_Pedido (numero_pedido, codigo_producto, cantidad) VALUES
(1,1,2),(1,2,5),
(2,3,1),(2,2,2),
(3,4,2),(3,5,1),
(4,1,1),(4,3,1),
(5,4,1),(5,5,2);

INSERT INTO Facturas (numero_pedido, id_cliente, fecha_emision, monto_total, estado_pago, id_empleado) VALUES
(1,1,'2026-01-10',32500,'pagado',2),
(2,2,'2026-01-11',4200,'pagado',4),
(3,3,'2026-01-12',15400,'pendiente',5),
(4,4,'2026-01-13',4500,'pagado',3),
(5,5,'2026-01-14',8700,'pendiente',1);
