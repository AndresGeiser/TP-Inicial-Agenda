/*************************/
/* Creacion de tablas	 */
/*************************/
CREATE TABLE IF NOT EXISTS paises (
	id int AUTO_INCREMENT NOT NULL,
	nombre varchar(100) NOT NULL,
	CONSTRAINT pk_paises PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS provincias (
	id int AUTO_INCREMENT NOT NULL,
	idPais int NOT NULL,
	nombre varchar(100) NOT NULL,
	CONSTRAINT pk_provincias PRIMARY KEY (id),
	CONSTRAINT fk_provincias_paises FOREIGN KEY (idPais) REFERENCES paises(id)
);

CREATE TABLE IF NOT EXISTS localidades (
	id int AUTO_INCREMENT NOT NULL,
	idProvincia int NOT NULL,
	nombre varchar(100) NOT NULL,
	CONSTRAINT pk_localidades PRIMARY KEY (id),
	CONSTRAINT fk_localidades_provincias FOREIGN KEY (idProvincia) REFERENCES provincias(id)
);

CREATE TABLE IF NOT EXISTS tipos (
	id int AUTO_INCREMENT NOT NULL, 
	nombre varchar(15) NOT NULL, 
	CONSTRAINT pk_tipos PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contactos (
	id int NOT NULL AUTO_INCREMENT,
	nombre varchar(20) NOT NULL,
	telefono varchar(15) NOT NULL,
	correo varchar(100),
	idTipo int,
	fecha_cumple date,
	idPais int,
	idProvincia int,
	idLocalidad int,
	calle varchar(255),
	altura varchar(6),
	tipo_domicilio enum('', 'Casa', 'Edificio'),
	piso varchar(3),
	dpto varchar(3),
	CONSTRAINT pk_personas PRIMARY KEY (id),
	CONSTRAINT fk_personas_tipos FOREIGN KEY (idTipo) REFERENCES tipos(id),
	CONSTRAINT fk_personas_paises FOREIGN KEY (idPais) REFERENCES paises(id),
	CONSTRAINT fk_personas_provincias FOREIGN KEY (idProvincia) REFERENCES provincias(id),
	CONSTRAINT fk_personas_localidades FOREIGN KEY (idLocalidad) REFERENCES localidades(id)
);


/******************************/
/* Insert de tipo de contacto */
/******************************/
INSERT IGNORE into tipos (id, nombre) VALUES (1, 'Trabajo');
INSERT IGNORE into tipos (id, nombre) VALUES (2, 'Universidad');
INSERT IGNORE into tipos (id, nombre) VALUES (3, 'Familia');
INSERT IGNORE into tipos (id, nombre) VALUES (4, 'Amigos');



/******************************/
/* 	Insert paises	*/
/******************************/
INSERT IGNORE into paises (id, nombre) VALUES (1, 'Argentina');
INSERT IGNORE into paises (id, nombre) VALUES (2, 'Uruguay');
INSERT IGNORE into paises (id, nombre) VALUES (3, 'Chile');


/******************************/
/* 	Insert provincias	*/
/******************************/
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (1, 1, 'Buenos Aires');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (2, 1, 'Catamarca');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (3, 1, 'Chaco');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (4, 1, 'Chubut');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (5, 1, 'Cordoba');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (6, 1, 'Corrientes');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (7, 1, 'Tucuman');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (8, 2, 'Artigas');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (9, 2, 'Canelones');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (10, 2, 'Cerro Largo');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (11, 2, 'Colonia');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (12, 2, 'Durazno');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (13, 3, 'Valparaiso');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (14, 3, 'Antofagasta');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (15, 3, 'Araucania');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (16, 3, 'Libertador General Bernardo OHiggins');
INSERT IGNORE into provincias (id, idPais, nombre) VALUES (17, 3, 'Region Metropolitana');

/******************************/
/* 	Insert localidades	*/
/******************************/
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (1, 1, 'Malvinas Argentinas');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (2, 1, 'San Miguel');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (3, 1, 'Jose C. Paz');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (4, 2, 'San Fernando del Valle de Catamarca');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (5, 2, 'Recreo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (6, 2, 'Fiambala');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (7, 3, 'Resistencia');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (8, 3, 'Barranqueras' );
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (9, 3, 'Villa Angela');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (10, 4, 'Trelew');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (11, 4, 'Puerto Madryn');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (12, 4, 'Esquel');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (13, 5, 'Alta Gracia');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (14, 5, 'Villa Maria');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (15, 5, 'Cosquin');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (16, 6, 'Mercedes');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (17, 6, 'Goya');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (18, 6, 'Ciudad de Corrientes');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (19, 7, 'Tafi Viejo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (20, 7, 'San Miguel de Tucuman');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (21, 7, 'Lules');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (22, 8, 'Diego Lamas');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (23, 8, 'Colonia Palma');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (24, 8, 'Sequeira');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (25, 9, 'Ciudad de la Costa');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (26, 9, 'Las Piedras');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (27, 9, 'Atlantida');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (28, 10, 'Melo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (29, 10, 'Arbolito');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (30, 10, 'Rio Branco');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (31, 11, 'Carmelo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (32, 11, 'Colonia del Sacramento');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (33, 11, 'Nueva Palmira');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (34, 12, 'La Paloma');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (35, 12, 'Carmen');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (36, 12, 'Blanquillo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (37, 13, 'Cabildo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (38, 13, 'Algarrobo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (39, 13, 'El Tabo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (40, 14, 'Maria Elena');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (41, 14, 'Mejillones');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (42, 14, 'San Pedro de Atacama');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (43, 15, 'Temuco');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (44, 15, 'Villarrica');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (45, 15, 'Pucon');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (46, 16, 'Santa Cruz');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (47, 16, 'Rancagua');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (48, 16, 'Rengo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (49, 17, 'San Bernardo');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (50, 17, 'Providencia');
INSERT IGNORE into localidades (id, idProvincia, nombre) VALUES (51, 17, 'Santiago de Chile');