-- ================================= --
--   INGENIERIA DE SOFTWARE 2018-2
-- ================================= --


-- --------------------------------- --
--  Caso de uso:
--       Ver Foro
--  Responsable:
--       Marco Iván Olea Olvera
-- --------------------------------- --

DROP TABLE IF EXISTS modeloForo.Pregunta;
CREATE TABLE modeloForo.Pregunta (
	id_pregunta SERIAL,
	pregunta TEXT,
	id_usuario INTEGER, 
	fecha_creada DATE,
	PRIMARY KEY (id_pregunta),
	CONSTRAINT autor
		FOREIGN KEY (id_usuario)
		REFERENCES modeloForo.Usuario (id)
);
COMMENT ON TABLE modeloForo.Pregunta IS
	'Contiene todas las preguntas que se han publicado en la página.';
	COMMENT ON COLUMN modeloForo.Pregunta.id_pregunta IS 
		'Identificador único de la pregunta.';
	COMMENT ON COLUMN modeloForo.Pregunta.pregunta IS 
		'Texto de la pregunta.';
	COMMENT ON COLUMN modeloForo.Pregunta.id_usuario IS 
		'El identificador único del usuario que realizó la pregunta.';
	COMMENT ON COLUMN modeloForo.Pregunta.fecha_creada IS 
		'La fecha de creación de la pregunta.';

DROP TABLE IF EXISTS modeloForo.Respuesta;
CREATE TABLE modeloForo.Respuesta (
	id_respuesta SERIAL,
	respuesta TEXT,
	id_usuario INTEGER, 
	fecha_creada DATE,
	id_pregunta INTEGER,
	PRIMARY KEY (id_respuesta),
	CONSTRAINT autor
		FOREIGN KEY (id_usuario)
		REFERENCES modeloForo.Usuario (id),
	CONSTRAINT pregunta
		FOREIGN KEY (id_pregunta)
		REFERENCES modeloForo.Pregunta (id_pregunta) 
);
COMMENT ON TABLE modeloForo.Respuesta IS
	'Contiene todas las respuestas que se han publicado en la página.';
	COMMENT ON COLUMN modeloForo.Respuesta.id_respuesta IS 
		'Identificador único de la respuesta.';
	COMMENT ON COLUMN modeloForo.Respuesta.respuesta IS 
		'Texto de la respuesta.';
	COMMENT ON COLUMN modeloForo.Respuesta.id_usuario IS 
		'El identificador único del usuario que contestó la pregunta.';
	COMMENT ON COLUMN modeloForo.Respuesta.fecha_creada IS 
		'La fecha de creación de la respeusta.';
	COMMENT ON COLUMN modeloForo.Respuesta.id_pregunta IS 
		'El identificador único de la pregunta que se está contestando.';



-- --------------------------------- --
--  Caso de uso:
--       Iniciar/Cerrar Sesion
--  Responsable:
--       Jose Luis Vazquez Lazaro
-- --------------------------------- --

--  Supongamos que se ha creado una BD
-- llamada ForoCiencias  y un esquema,
-- de esta BD, llamado modeloForo.

drop extension if exists pgcrypto;
create extension pgcrypto;

--  La tabla usuario contendra la info
-- que un  Usuario  de  la  aplicacion
-- registro    para    poder   iniciar 
-- sesion en el sistema.
drop table if exists modeloForo.usuario;

create table modeloForo.usuario (
	id serial primary key,
	nombreUsuario text not null,
	correoCiencias text not null,
	password text not null,
	genero text not null,
	fechaNac date not null,
	constraint usuarioUnico unique (usuario)
);



-- --------------------------------- --
--  Caso de uso:
--       Registrar
--  Responsable:
--       Raziel Almanza Ibarra
-- --------------------------------- --

-- Usando la BD ForoCiencias
-- y un esquema de la misma, modeloForo

-- Insertando en la tabla usuario

insert into usuario(
	nombreUsuario,
	correoCiencias,
	password,
	genero,
	fechaNac
	)values(
	val1,
	val2,
	val3,
	val4
	);



-- --------------------------------- --
--  Caso de uso:
--       Agregar respuesta
--  Responsable:
--       Ximena Lezama Hernández
-- --------------------------------- --

-- ENTITDAD
CREATE TABLE AGREGA_RESPUESTA(
    NO_PREGUNTA INT NOT NULL,
    NOM_USUARIO_PREGUNTA VARCHAR2(255) NOT NULL,
    NOM_USUARIO_RESPUESTA VARCHAR2(255) NOT NULL,
    PREGUNTA VARCHAR2(255) NOT NULL,
    RESPUESTA VARCHAR2(255),
    PRIMARY KEY(NO_PREGUNTA)
    
);
