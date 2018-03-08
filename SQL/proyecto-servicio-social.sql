-- ================================= --
--   INGENIERIA DE SOFTWARE 2018-2
-- ================================= --

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
