/*************************/
/* Tablas para contactos */
/*************************/
CREATE TABLE IF NOT EXISTS tipos (
	id int AUTO_INCREMENT NOT NULL, 
	nombre varchar(100) NOT NULL, 
	CONSTRAINT pk_tipos PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contactos (
	id int NOT NULL AUTO_INCREMENT,
	nombre varchar(20) NOT NULL  UNIQUE,
	telefono varchar(20) NOT NULL,
	correo varchar(100),
	idTipo int,
	fecha_cumple varchar(255),
	pais varchar(255),
	provincia varchar(255),
	localidad varchar(255),
	calle varchar(255),
	altura varchar(6),
	tipo_domicilio enum('', 'Casa', 'Edificio'),
	piso varchar(3),
	dpto varchar(3),
	CONSTRAINT pk_personas PRIMARY KEY (id),
	CONSTRAINT fk_personas_tipos FOREIGN KEY (idTipo) REFERENCES tipos(id)
);


/******************************/
/* Insert de tipo de contacto */
/******************************/
INSERT IGNORE into tipos (id, nombre) VALUES (1, 'Trabajo');
INSERT IGNORE into tipos (id, nombre) VALUES (2, 'Universidad');
INSERT IGNORE into tipos (id, nombre) VALUES (3, 'Familia');
INSERT IGNORE into tipos (id, nombre) VALUES (4, 'Amigos');