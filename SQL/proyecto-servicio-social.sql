-- ================================= --
--   INGENIERIA DE SOFTWARE 2018-2
--          SCRUM SOFTWARE
-- --------------------------------- --
--  Responsable:
--       Jose Luis Vazquez Lazaro
-- ================================= --


-- ================================= --
--  Base de Datos y Esquema
-- ================================= --

--  Administrador del sistema.
BEGIN;
DROP ROLE IF EXISTS Administrador;
CREATE ROLE Administrador WITH SUPERUSER;
ALTER ROLE Administrador WITH LOGIN;

--  Base  de  Datos del sistema y un 
-- un esquema para esta.
DROP DATABASE IF EXISTS ForoCiencias;
CREATE DATABASE ForoCiencias;

DROP SCHEMA IF EXISTS modeloForo CASCADE;
CREATE SCHEMA modeloForo;


-- ================================= --
--  Tablas
-- ================================= --

--  La tabla  Usuario  contendra  la
-- informacion  que  un  Usuario  de
-- la aplicacion registro para poder
-- iniciar sesion en el sistema.
DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;

DROP TABLE IF EXISTS modeloForo.Usuario;
CREATE TABLE modeloForo.Usuario (
	idUsuario	 SERIAL PRIMARY KEY,
	nombreUsuario	 TEXT NOT NULL,
	correoCiencias	 TEXT NOT NULL,
	contrasena	 TEXT NOT NULL,
	genero		 TEXT NOT NULL,
	fechaNacimiento  DATE NOT NULL,
	cuentaVerificada TEXT NOT NULL,
	administrador	 TEXT NOT NULL,
	CONSTRAINT usuarioUnico UNIQUE ( nombreUsuario ),
	CONSTRAINT correoUnico UNIQUE ( correoCiencias ),
	CONSTRAINT nombreUsuarioValido CHECK ( nombreUsuario ~ '^(\w|\.|\-)+$' ),
	CONSTRAINT correoValido CHECK ( correoCiencias ~ '^((\w|\.|\-)+)@ciencias.unam.mx$' ),
	CONSTRAINT generoValido CHECK ( genero ~ 'H|M' ),
	CONSTRAINT cuentaValida CHECK ( cuentaVerificada ~ 'S|N' ),
	CONSTRAINT administradorValido CHECK ( administrador ~ 'S|N' )
);
COMMENT ON TABLE modeloForo.Usuario IS 'Contiene información de cada usuario.';
COMMENT ON COLUMN modeloForo.Usuario.idUsuario IS 'El identificador único de cada usuario';
COMMENT ON COLUMN modeloForo.Usuario.nombreUsuario IS 'El nombre del usuario.';
COMMENT ON COLUMN modeloForo.Usuario.correoCiencias IS 'El correo electrónico del usuario.';
COMMENT ON COLUMN modeloForo.Usuario.contrasena IS 'El resultado de una función hash que recibe la contraseña del usuario como parámetro.';
COMMENT ON COLUMN modeloForo.Usuario.genero IS 'El genero del usuario.';
COMMENT ON COLUMN modeloForo.Usuario.fechaNacimiento IS 'La fecha de nacimiento del usuario.';
COMMENT ON COLUMN modeloForo.Usuario.cuentaVerificada IS 'Indica si la cuenta ha sido verificada.';
COMMENT ON CONSTRAINT nombreUsuarioValido ON modeloForo.usuario IS 'El nombre del usuario debe contener al menos 1 y a lo más 20 letras sin acentos, números, ''.'', ''-'' ó ''_''.';
COMMENT ON CONSTRAINT correoValido ON modeloForo.usuario IS 'El correo del usuario debe ser <c>@ciencias.unam.mx, donde <c> es al menos una letra sin acento, número, ''.'', ''-'' ó ''_''.';
COMMENT ON CONSTRAINT generoValido ON modeloForo.usuario IS 'El sexo debe ser o bien ''H'' o bien ''M''';
COMMENT ON CONSTRAINT cuentaValida ON modeloForo.usuario IS '''S'' si la cuenta está validad, ''N'' en otro caso';
COMMENT ON CONSTRAINT administradorValido ON modeloForo.usuario IS '''S'' si el usuario es administrador, ''N'' en otro caso';

--  La tabla Pregunta contendra cada
-- una de  las preguntas  hechas por
-- los Usuario del sistema.
DROP TABLE IF EXISTS modeloForo.Pregunta;
CREATE TABLE modeloForo.Pregunta (
	idPregunta SERIAL,
	titulo TEXT NOT NULL,
	contenido TEXT NOT NULL,
	fechaPregunta TIMESTAMP NOT NULL,
	idUsuario SERIAL,
	CONSTRAINT preguntaAutor FOREIGN KEY ( idusuario ) REFERENCES modeloForo.usuario ( idUsuario ),
	CONSTRAINT preguntaPK PRIMARY KEY ( idPregunta, idUsuario )
);
COMMENT ON TABLE modeloForo.pregunta IS 'Contiene información de cada pregunta.';
COMMENT ON COLUMN modeloForo.pregunta.idPregunta IS 'El identificador de la pregunta; es único con respecto al usuario asociado.';
COMMENT ON COLUMN modeloForo.pregunta.titulo IS 'El título de la pregunta.';
COMMENT ON COLUMN modeloForo.pregunta.contenido IS 'El contenido de la pregunta.';
COMMENT ON COLUMN modeloForo.pregunta.fechaPregunta IS 'La fecha y la hora en que se realizó la pregunta.';
COMMENT ON COLUMN modeloForo.pregunta.idUsuario IS 'El identificador del usuario que realizó la pregunta.';
COMMENT ON CONSTRAINT preguntaAutor ON modeloForo.pregunta IS 'La referencia al usuario que realizó la pregunta.';

--  La tabla Respuesta contendra cada
-- una de  las preguntas  hechas  por
-- los Usuario del sistema.
DROP TABLE IF EXISTS modeloForo.Respuesta;
CREATE TABLE modeloForo.Respuesta (
	idRespuesta SERIAL,
	contenido TEXT NOT NULL,
	fechaRespuesta TIMESTAMP NOT NULL,
	idPregunta SERIAL,
	idUsuario SERIAL,
	CONSTRAINT respuestaAutor FOREIGN KEY ( idPregunta, idUsuario ) REFERENCES modeloForo.pregunta ( idPregunta, idUsuario ),
	CONSTRAINT respuestaPK PRIMARY KEY ( idRespuesta, idPregunta, idUsuario )
);
COMMENT ON TABLE modeloForo.respuesta IS 'Contiene información de cada respuesta.';
COMMENT ON COLUMN modeloForo.respuesta.idRespuesta IS 'El identificador de la respuesta; es único con respecto al usuario y pregunta asociados.';
COMMENT ON COLUMN modeloForo.respuesta.contenido IS 'El contenido de la respuesta.';
COMMENT ON COLUMN modeloForo.respuesta.fechaRespuesta IS 'La fecha y hora en que se contestó la pregunta.';
COMMENT ON COLUMN modeloForo.respuesta.idUsuario IS 'El identificador del usuario que contestó la pregunta.';
COMMENT ON COLUMN modeloForo.respuesta.idPregunta IS 'El identificador de la pregunta que contestó la pregunta.';
COMMENT ON CONSTRAINT respuestaAutor ON modeloForo.respuesta IS 'La referencia al usuario y la pregunta que realizó este.';


-- ================================= --
--  Triggers y funciones
-- ================================= --

--  Permite cifrar la  contraseña de 
-- cada Usuario registrado.
COMMENT ON TABLE modeloForo.Usuario IS 'El usuario NOMBREUSUARIO tiene la contraseña CONTRASENA después de aplicarle un hash.';

CREATE OR REPLACE FUNCTION modeloForo.resguardar() RETURNS TRIGGER AS
$$
begin
	if TG_OP = 'INSERT' then
		NEW.contrasena = crypt( NEW.contrasena, gen_salt( 'bf', 8 )::text );
	end if;
	return NEW;
end;
$$ language plpgsql;

COMMENT ON FUNCTION modeloForo.resguardar() IS 'Cifra la contraseña del usuario al guardarla en la base de datos.';

CREATE TRIGGER cifra BEFORE INSERT ON modeloForo.Usuario FOR EACH ROW EXECUTE PROCEDURE modeloForo.resguardar();

--  Indica si un usuario esta o no
-- registrado en la base de datos
-- del sistema.
CREATE OR REPLACE FUNCTION modeloForo.verificar( nombre TEXT, pass TEXT ) RETURNS BOOLEAN AS
$$
	SELECT EXISTS( SELECT 1
		       FROM   modeloForo.Usuario
                       WHERE  nombreUsuario = nombre AND
                              contrasena = crypt( pass, contrasena ) AND
                              cuentaVerificada ~ 'S' );
$$ language sql stable;

--  Elimina una pregunta y todas
-- las respuesta a sociadas a
-- esta dentro del sistema.
CREATE OR REPLACE FUNCTION modeloForo.eliminarPregunta( idP TEXT ) RETURNS BOOLEAN AS
$$
DECLARE
	a INT;
BEGIN
	a := CAST( idP AS INT );
	
	DELETE
	FROM modeloForo.Respuesta
	WHERE IdPregunta = a;

	DELETE
	FROM modeloForo.Pregunta
	WHERE IdPregunta = a;

	RETURN true;
END;
$$ language plpgsql volatile;

--  Elimina una pregunta dentro
-- de la base de datos
CREATE OR REPLACE FUNCTION modeloForo.eliminarRespuesta( idR TEXT ) RETURNS BOOLEAN AS
$$
DECLARE
	a INT;
BEGIN
	a := CAST( idR AS INT );
	
	DELETE
	FROM modeloForo.Respuesta
	WHERE IdPregunta = a;

	RETURN true;
END;
$$ language plpgsql volatile;

-- ================================= --
--  Registros de prueba
-- ================================= --
INSERT INTO modeloForo.Usuario( nombreUsuario, correoCiencias, contrasena, genero, fechaNacimiento, cuentaVerificada, administrador ) VALUES ( 'LuisLazaro88', 'luis_lazaro@ciencias.unam.mx', 'password', 'H', '1988-02-25', 'S', 'S' );
INSERT INTO modeloForo.Usuario( nombreUsuario, correoCiencias, contrasena, genero, fechaNacimiento, cuentaVerificada, administrador ) VALUES ( 'Miguel', 'miguel_pinia@ciencias.unam.mx', 'pinia88', 'H', '1988-02-25', 'S', 'N' );

INSERT INTO modeloForo.Pregunta( titulo, contenido, fechaPregunta, idUsuario ) VALUES ( '¿IS es obligatoria?', 'En caso de que sea obligatoria, ¿qué tan pesada es la materia?', '2018-05-08', 1 );

INSERT INTO modeloForo.Respuesta( contenido, fechaRespuesta, idPregunta, idUsuario ) VALUES ( 'La materia si es pesada, pero afortunadamente trabajas en equipo', '2018-05-08', 1, 1 );

COMMIT;
