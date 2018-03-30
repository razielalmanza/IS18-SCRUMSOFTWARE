/* Esquema usuarios. */

DROP SCHEMA IF EXISTS usuarios CASCADE;
CREATE SCHEMA usuarios;
COMMENT ON SCHEMA usuarios IS 
	'Esquema que contiene la información de los usuarios.';
;

CREATE TABLE usuarios.usuario (
	id_usuario BIGINT,
	nombre_usuario VARCHAR(20) UNIQUE NOT NULL,
	correo VARCHAR(100) UNIQUE NOT NULL,
	contrasenia TEXT NOT NULL,
	sexo CHAR(1) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	CONSTRAINT usuario_pkey
		PRIMARY KEY (id_usuario),
	CONSTRAINT usuario_nombre_usuario_valido
		CHECK (nombre_usuario ~ '^(\w|\.|\-)+$'),
	CONSTRAINT usuario_correo_valido
		CHECK (correo ~ '^((\w|\.|\-)+)@((\w|\.|\-)+)$'),
	CONSTRAINT usuario_sexo_valido
		CHECK (sexo ~ 'H|M')
);
COMMENT ON TABLE usuarios.usuario IS 
	'Contiene información de cada usuario.';
	COMMENT ON COLUMN usuarios.usuario.id_usuario IS 
		'El identificador único de cada usuario';
	COMMENT ON COLUMN usuarios.usuario.nombre_usuario IS 
		'El nombre del usuario.';
	COMMENT ON COLUMN usuarios.usuario.correo IS 
		'El correo electrónico del usuario.';
	COMMENT ON COLUMN usuarios.usuario.contrasenia IS 
		'El resultado de una función hash que recibe la contraseña del usuario como parámetro.';
	COMMENT ON COLUMN usuarios.usuario.sexo IS 
		'El sexo del usuario.';
	COMMENT ON COLUMN usuarios.usuario.fecha_nacimiento IS 
		'La fecha de nacimiento del usuario.';
	COMMENT ON CONSTRAINT usuario_nombre_usuario_valido ON usuarios.usuario IS
		'El nombre del usuario debe contener al menos 1 y a lo más 20 letras sin acentos, números, ''.'', ''-'' ó ''_''.';
	COMMENT ON CONSTRAINT usuario_correo_valido ON usuarios.usuario IS
		'El correo del usuario debe ser <c>@<c>, donde <c> es al menos una letra sin acento, número, ''.'', ''-'' ó ''_''.';
	COMMENT ON CONSTRAINT usuario_sexo_valido ON usuarios.usuario IS
		'El sexo debe ser o bien ''H'' o bien ''M''';
;


/* Esquema preguntas. */

DROP SCHEMA IF EXISTS preguntas CASCADE;
CREATE SCHEMA preguntas;
COMMENT ON SCHEMA preguntas IS 
	'Esquema que contiene la información de preguntas y respuestas que hacen los usuarios.';
;

CREATE TABLE preguntas.pregunta (
	id_usuario BIGINT,
	id_pregunta BIGINT NOT NULL,
	titulo TEXT NOT NULL,
	contenido TEXT NOT NULL,
	fecha_y_hora TIMESTAMP NOT NULL,
	CONSTRAINT pregunta_autor
		FOREIGN KEY (id_usuario)
		REFERENCES usuarios.usuario (id_usuario),
	CONSTRAINT pregunta_pkey
		PRIMARY KEY (id_usuario, id_pregunta)
);
COMMENT ON TABLE preguntas.pregunta IS 
	'Contiene información de cada pregunta.';
	COMMENT ON COLUMN preguntas.pregunta.id_usuario IS 
		'El identificador del usuario que realizó la pregunta.';
	COMMENT ON COLUMN preguntas.pregunta.id_pregunta IS 
		'El identificador de la pregunta; es único con respecto al usuario asociado.';
	COMMENT ON COLUMN preguntas.pregunta.titulo IS 
		'El título de la pregunta.';
	COMMENT ON COLUMN preguntas.pregunta.contenido IS 
		'El contenido de la pregunta.';
	COMMENT ON COLUMN preguntas.pregunta.fecha_y_hora IS 
		'La fecha y la hora en que se realizó la pregunta.';
	COMMENT ON CONSTRAINT pregunta_autor ON preguntas.pregunta IS
		'La referencia al usuario que realizó la pregunta.';
;

CREATE TABLE preguntas.respuesta (
	id_usuario BIGINT,
	id_pregunta BIGINT,
	id_respuesta BIGINT NOT NULL,
	contenido TEXT NOT NULL,
	fecha_y_hora TIMESTAMP NOT NULL,
	CONSTRAINT respuesta_autor
		FOREIGN KEY (id_usuario, id_pregunta)
		REFERENCES preguntas.pregunta (id_usuario, id_pregunta)
		ON DELETE CASCADE,
	CONSTRAINT respuesta_pkey
		PRIMARY KEY (id_usuario, id_pregunta, id_respuesta)
);
COMMENT ON TABLE preguntas.respuesta IS 
	'Contiene información de cada respuesta.';
	COMMENT ON COLUMN preguntas.respuesta.id_usuario IS 
		'El identificador del usuario que contestó la pregunta.';
	COMMENT ON COLUMN preguntas.respuesta.id_pregunta IS 
		'El identificador de la pregunta que contestó la pregunta.';
	COMMENT ON COLUMN preguntas.respuesta.id_respuesta IS 
		'El identificador de la respuesta; es único con respecto al usuario y pregunta asociados.';
	COMMENT ON COLUMN preguntas.respuesta.contenido IS 
		'El contenido de la respuesta.';
	COMMENT ON COLUMN preguntas.respuesta.fecha_y_hora IS 
		'La fecha y hora en que se contestó la pregunta.';
	COMMENT ON CONSTRAINT respuesta_autor ON preguntas.respuesta IS
		'La referencia al usuario y la pregunta que realizó este.';
;


/* Triggers. */

DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;
COMMENT ON SCHEMA public IS 
	'Esquema que contiene objetos compartidos entre los demás esquemas.';
;

CREATE OR REPLACE FUNCTION public.func_custom_id()
RETURNS TRIGGER AS $$
    DECLARE
        new_id BIGINT;
        full_name TEXT := TG_TABLE_SCHEMA || '.' || TG_TABLE_NAME;
    BEGIN
        IF full_name = 'usuarios.usuario' THEN 
            new_id = 
                COALESCE(MAX(id_usuario), 0) 
                FROM usuarios.usuario;
            NEW.id_usuario = new_id + 1;
        ELSIF full_name = 'preguntas.pregunta' THEN
            new_id = 
                COALESCE(MAX(id_pregunta), 0) 
                FROM preguntas.pregunta 
                WHERE id_usuario = NEW.id_usuario;
            NEW.id_pregunta = new_id + 1;
        ELSIF full_name = 'preguntas.respuesta' THEN
            new_id = 
                COALESCE(MAX(id_respuesta), 0) 
                FROM preguntas.respuesta 
                WHERE id_usuario = NEW.id_usuario AND id_pregunta = NEW.id_pregunta;
            NEW.id_respuesta = new_id + 1;
        END IF;
        RETURN NEW;
    END
$$ LANGUAGE PLPGSQL;
COMMENT ON FUNCTION func_custom_id() IS 
    'Función que asevera que siempre tendrán identificadores válidos las tablas que así lo requieran.';

CREATE TRIGGER tg_set_custom_id_usuario
BEFORE INSERT ON usuarios.usuario 
FOR EACH ROW EXECUTE PROCEDURE public.func_custom_id();
COMMENT ON TRIGGER tg_set_custom_id_usuario ON usuarios.usuario IS 
    'Trigger que fija identificadores válidos para cada usuario.';

CREATE TRIGGER tg_set_custom_id_pregunta
BEFORE INSERT ON preguntas.pregunta
FOR EACH ROW EXECUTE PROCEDURE public.func_custom_id();
COMMENT ON TRIGGER tg_set_custom_id_pregunta ON preguntas.pregunta IS 
    'Trigger que fija identificadores válidos para cada pregunta, con respecto al usuario que la realizó.';

CREATE TRIGGER tg_set_custom_id_respuesta
BEFORE INSERT ON preguntas.respuesta
FOR EACH ROW EXECUTE PROCEDURE public.func_custom_id();
COMMENT ON TRIGGER tg_set_custom_id_respuesta ON preguntas.respuesta IS 
    'Trigger que fija identificadores válidos para cada respuesta, con respecto al usuario y pregunta.';

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE OR REPLACE FUNCTION usuarios.func_hash_contrasenia()
RETURNS TRIGGER AS $$
    BEGIN
        NEW.contrasenia = CRYPT(NEW.contrasenia, GEN_SALT('bf', 8));
        RETURN NEW;
    END
$$ LANGUAGE PLPGSQL;
COMMENT ON FUNCTION usuarios.func_hash_contrasenia IS 
    'Función que asevera que siempre tendrán identificadores válidos las tablas que así lo requieran.';

CREATE TRIGGER tg_hash_contrasenia
BEFORE INSERT OR UPDATE ON usuarios.usuario 
FOR EACH ROW EXECUTE PROCEDURE usuarios.func_hash_contrasenia();
COMMENT ON TRIGGER tg_hash_contrasenia ON usuarios.usuario IS 
    'Trigger que pasa la contraseña del usuario por una función hash.';