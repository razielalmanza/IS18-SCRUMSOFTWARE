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