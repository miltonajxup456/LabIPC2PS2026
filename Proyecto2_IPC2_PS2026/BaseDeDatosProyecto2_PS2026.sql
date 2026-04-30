DROP DATABASE IF EXISTS Proyecto2_PS2026;
CREATE DATABASE IF NOT EXISTS Proyecto2_PS2026;

-- SHOW VARIABLES LIKE 'validate_password%';
SET GLOBAL validate_password.policy = LOW;
SET GLOBAL validate_password.length = 4;
CREATE USER IF NOT EXISTS 'usuarioP1'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON Proyecto2_PS2026.* TO 'usuarioP1'@'localhost';
FLUSH PRIVILEGES;

-- DROP USER IF EXISTS 'usuarioP1'@'localhost';

USE Proyecto2_PS2026;

CREATE TABLE Rol(
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    tipo_rol VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO Rol (tipo_rol) VALUES 
("ADMINISTRADOR"),
("CLIENTE"),
("FREELANCER");

CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion_categoria VARCHAR(300) NOT NULL,
    habilitado BOOLEAN DEFAULT TRUE
);

CREATE TABLE Habilidad (
    id_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_habilidad VARCHAR(50) NOT NULL,
    descripcion_habilidad VARCHAR(200) NOT NULL
);

CREATE TABLE Habilidad_Categoria (
    id_habilidad_categoria INT AUTO_INCREMENT PRIMARY KEY,
    categoria INT NOT NULL,
    habilidad INT NOT NULL,
    UNIQUE (categoria, habilidad),
    FOREIGN KEY (categoria) REFERENCES Categoria(id_categoria) ON DELETE CASCADE,
    FOREIGN KEY (habilidad) REFERENCES Habilidad(id_habilidad) ON DELETE CASCADE
);

CREATE TABLE Solicitud_Categoria (
    id_solicitud_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    aprobado BOOLEAN DEFAULT FALSE
);

CREATE TABLE Solicitud_Habilidad (
    id_solicitud_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    aprobado BOOLEAN DEFAULT FALSE
);

CREATE TABLE Comision (
    id_comision INT AUTO_INCREMENT PRIMARY KEY,
    porcentaje INT NOT NULL,
    fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_final DATETIME
);

INSERT INTO Comision (porcentaje) VALUES (10);

CREATE TABLE Usuario (
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    password_user VARCHAR(255) NOT NULL, 
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(100),
    cui VARCHAR(20),
    fecha_nac DATE,
    -- informacion_usuario VARCHAR(200),
    baneo BOOLEAN DEFAULT FALSE,
    saldo DECIMAL(10,2) DEFAULT 0,
    rol INT NOT NULL,
    FOREIGN KEY (rol) REFERENCES Rol(id_rol)
);

INSERT INTO Usuario (nombre_usuario, nombre, password_user, correo_electronico, telefono, direccion, cui, fecha_nac, rol)
VALUES ("Milton1", "Milton", "1234", "mil@gmail.com", "12345678", "Guatemala", "87654321", "2004-01-01", 2),
("Milton2", "Efren", "1234", "efren@gmail.com", "12345678", "guatemala", "87654321", "2000-01-01", 3);

CREATE TABLE Cliente(
    id_cliente VARCHAR(50) NOT NULL PRIMARY KEY,
    descripcion_empresa VARCHAR(200) NOT NULL,
    industria_perteneciente VARCHAR(100) NOT NULL,
    sitio_web VARCHAR(100),
    FOREIGN KEY (id_cliente) REFERENCES Usuario(nombre_usuario)
);

CREATE TABLE Recarga(
    id_recarga INT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente VARCHAR(50) NOT NULL,
    FOREIGN KEY (cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Nivel_De_Experiencia (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    tipo_nivel VARCHAR(25) NOT NULL
);

INSERT INTO Nivel_De_Experiencia (tipo_nivel) VALUES 
("JUNIOR"),
("SEMI-SENIOR"),
("SENIOR");

CREATE TABLE Freelancer (
    id_freelancer VARCHAR(50) NOT NULL PRIMARY KEY,
    biografia VARCHAR(500) NOT NULL,
    tarifa_referencial DECIMAL(10,2),
    nivel_experiencia INT NOT NULL,
    FOREIGN KEY (id_freelancer) REFERENCES Usuario(nombre_usuario),
    FOREIGN KEY (nivel_experiencia) REFERENCES Nivel_De_Experiencia(id_nivel)
);

CREATE TABLE Habilidad_Freelancer (
    id_habilidad_freelancer INT AUTO_INCREMENT PRIMARY KEY,
    habilidad INT NOT NULL,
    freelancer VARCHAR(50) NOT NULL,
    FOREIGN KEY (habilidad) REFERENCES Habilidad(id_habilidad) ON DELETE CASCADE,
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer)
);

CREATE TABLE Calificacion_Freelancer (
    id_calificacion INT AUTO_INCREMENT PRIMARY KEY,
    calificacion INT NOT NULL,
    comentario VARCHAR(200),
    cliente VARCHAR(50) NOT NULL,
    freelancer VARCHAR(50) NOT NULL,
    FOREIGN KEY (cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer)
);

CREATE TABLE Estado_Proyecto (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    tipo_estado VARCHAR(25),
    descripcion VARCHAR(200)
);

INSERT INTO Estado_Proyecto (tipo_estado, descripcion) VALUES 
("ABIERTO", "El proyecto fue publicado por el cliente y está recibiendo propuestas de freelancers."), 
("EN_REVISION", "El cliente seleccionó una propuesta y está en proceso de confirmar el contrato."), 
("EN_PROGRESO", "El contrato fue firmado y el freelancer está trabajando en el proyecto."), 
("ENTREGA_PENDIENTE", "El freelancer subió una entrega y el cliente debe revisar y aprobar o rechazarla."), 
("COMPLETADO", "El cliente aprobó la entrega final y el pago fue liberado al freelancer."), 
("CANCELADO", "El cliente canceló el contrato activo indicando el motivo. El monto bloqueado es reembolsado al cliente.");

CREATE TABLE Proyecto (
    id_proyecto INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    presupuesto DECIMAL(10,2) NOT NULL,
    fecha_limite DATE NOT NULL,
    -- comision INT 
    cliente VARCHAR(50) NOT NULL,
    categoria INT NOT NULL,
    estado INT NOT NULL,
    FOREIGN KEY (cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (categoria) REFERENCES Categoria(id_categoria),
    FOREIGN KEY (estado) REFERENCES Estado_Proyecto(id_estado)
);

CREATE TABLE Habilidad_Proyecto (
    id_habilidad_proyecto INT AUTO_INCREMENT PRIMARY KEY,
    habilidad INT NOT NULL,
    proyecto INT NOT NULL,
    FOREIGN KEY (habilidad) REFERENCES Habilidad(id_habilidad) ON DELETE CASCADE,
    FOREIGN KEY (proyecto) REFERENCES Proyecto(id_proyecto)
);

CREATE TABLE Propuesta_Proyecto (
    id_propuesta INT AUTO_INCREMENT PRIMARY KEY,
    presentacion VARCHAR(300) NOT NULL,
    monto_ofertado DECIMAL(10,2) NOT NULL,
    plazo_entrega_propuesto INT NOT NULL,
    -- estado INT NOT NULL,
    freelancer VARCHAR(50) NOT NULL,
    proyecto INT NOT NULL,
    -- FOFEIGN KEY (estado) REFERENCES Estado_Proyecto(id_estado),
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer) ON DELETE CASCADE,
    FOREIGN KEY (proyecto) REFERENCES Proyecto(id_proyecto)
);

CREATE TABLE Entrega_Proyecto (
    id_entrega INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL,
    path_archivo VARCHAR(200) NOT NULL,
    proyecto INT NOT NULL,
    freelancer VARCHAR(50) NULL,
    FOREIGN KEY (proyecto) REFERENCES Proyecto(id_proyecto),
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer) ON DELETE SET NULL
);

CREATE TABLE Respuesta_Entrega_Proyecto (
    id_respuesta INT AUTO_INCREMENT PRIMARY KEY,
    respuesta VARCHAR(200) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    proyecto INT NOT NULL,
    freelancer VARCHAR(50) NOT NULL,
    FOREIGN KEY (proyecto) REFERENCES Proyecto(id_proyecto),
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer)
);

CREATE TABLE Historial_Comision (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    monto_proyecto DECIMAL(10,2) NOT NULL,
    porcentaje_comision INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente VARCHAR(50) NOT NULL,
    freelancer VARCHAR(50) NOT NULL,
    FOREIGN KEY (cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (freelancer) REFERENCES Freelancer(id_freelancer)
);

INSERT INTO Categoria (nombre_categoria, descripcion_categoria) VALUES 
("Diseño (UI/UX)", 
"Creación de interfaces visuales atractivas y funcionales, enfocadas en la experiencia del usuario. Incluye el 
uso de colores, tipografía y estructura para lograr navegación clara, aplicando HTML y CSS para construir 
diseños modernos y accesibles."), 
("Desarrollo Web", 
"Proceso de crear sitios y aplicaciones web, integrando frontend (interfaz visual) y backend (lógica y datos). 
Utiliza tecnologías como HTML, CSS, JavaScript y servidores para implementar funcionalidades eficientes y 
dinámicas."), 
("Marketing Digital", 
"Estrategias para atraer y retener usuarios en entornos web mediante SEO, análisis de datos y optimización 
del rendimiento. Se vincula al desarrollo al mejorar visibilidad, velocidad del sitio e integración de 
herramientas digitales."), 
("Redacción Web", 
"Creación de contenido claro, útil y optimizado para plataformas digitales. Incluye textos para interfaces, 
documentación y SEO, facilitando la comunicación entre el usuario y el sistema, y mejorando la experiencia 
general.");

INSERT INTO Habilidad (nombre_habilidad, descripcion_habilidad) VALUES 
("Diseño de interfaces (UI)", "Creación de layouts visuales claros y atractivos."), 
("Experiencia de usuario (UX)", "Mejora de la usabilidad y navegación del sitio."), 
("Prototipado (Figma, Adobe XD)", "Desarrollo de maquetas interactivas previas."), 
("Diseño responsivo", "Adaptación a distintos tamaños de pantalla."), 
("Tipografía y color", "Uso adecuado para jerarquía visual."), 
("Wireframing", "Estructuración básica de interfaces."), 
("HTML, CSS, JavaScript", "Base para construir sitios web."), 
("Desarrollo frontend", "Implementación de interfaces interactivas."), 
("Consumo de APIs", "Integración de servicios externos."), 
("Control de versiones (Git)", "Gestión de cambios en el código."), 
("Diseño responsivo", "Implementación adaptable en código."), 
("Optimización de rendimiento", "Mejora de velocidad y eficiencia."), 
("SEO", "Mejora del posicionamiento en buscadores."), 
("Análisis de datos", "Evaluación del comportamiento del usuario."), 
("Optimización web", "Ajustes para mejorar conversión y rendimiento."), 
("Estrategias de contenido", "Planificación orientada a atraer usuarios."), 
("Redes sociales", "Gestión básica de presencia digital."), 
("Redacción SEO", "Contenido optimizado para buscadores."), 
("UX Writing", "Textos claros dentro de interfaces."), 
("Documentación técnica", "Explicación de procesos o sistemas."), 
("Contenido digital", "Creación de textos para web."), 
("Corrección y estilo", "Mejora de claridad y coherencia.");

INSERT INTO Habilidad_Categoria (categoria, habilidad) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), 
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), 
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), 
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5);
