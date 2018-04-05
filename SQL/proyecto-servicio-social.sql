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
	idUsuario	SERIAL PRIMARY KEY,
	nombreUsuario	TEXT NOT NULL,
	correoCiencias	TEXT NOT NULL,
	contrasena	TEXT NOT NULL,
	genero		TEXT NOT NULL,
	fechaNacimiento DATE NOT NULL,
	CONSTRAINT usuarioUnico UNIQUE (nombreUsuario)
);


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

--  La tabla  Usuario  contendra  la
-- informacion  que  un  Usuario  de
-- la aplicacion registro para poder
-- iniciar sesion en el sistema.
CREATE OR REPLACE FUNCTION modeloForo.verificar( nombre TEXT, pass TEXT ) RETURNS BOOLEAN AS
$$
	SELECT EXISTS( SELECT 1
		       FROM   modeloForo.Usuario
                       WHERE  nombreUsuario = nombre AND contrasena = crypt( pass, contrasena ) );
$$ language sql stable;


-- ================================= --
--  Registros de prueba
-- ================================= --
INSERT INTO modeloForo.Usuario( nombreUsuario, correoCiencias, contrasena, genero, fechaNacimiento ) VALUES ( 'LuisLazaro88', 'luis_lazaro@ciencias.unam.mx', 'password', 'Masculino', '1988-02-25' );
INSERT INTO modeloForo.Usuario( nombreUsuario, correoCiencias, contrasena, genero, fechaNacimiento ) VALUES ( 'Miguel', 'miguel_pinia@ciencias.unam.mx', 'pinia88', 'Masculino', '1988-02-25' );

COMMIT;