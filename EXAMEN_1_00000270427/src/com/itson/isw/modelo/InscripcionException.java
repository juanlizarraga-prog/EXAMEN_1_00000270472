package com.itson.isw.modelo;

/**
 * Se lanza cuando se viola una regla de negocio de la inscripción
 * (alumno inexistente, alumno ya inscrito, taller sin cupo, etc.).
 */
public class InscripcionException extends Exception {

    public InscripcionException(String mensaje) {
        super(mensaje);
    }
}
