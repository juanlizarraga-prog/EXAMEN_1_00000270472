package com.itson.isw.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo: relación entre un alumno y un taller.
 * El registro de inscripciones está en memoria (reemplazar por BD).
 */
public class Inscripcion {

    private static final List<Inscripcion> REGISTRO = new ArrayList<>();

    private final Alumno alumno;
    private final Taller taller;
    private final LocalDateTime fechaInscripcion;
    private String estatus;

    private Inscripcion(Alumno alumno, Taller taller) {
        this.alumno = alumno;
        this.taller = taller;
        this.fechaInscripcion = LocalDateTime.now();
        this.estatus = "INSCRITO";
    }

    /** Operación de clase: ¿el alumno ya está inscrito en ese taller? */
    public static boolean existeInscripcion(String idAlumno, int idTaller) {
        for (Inscripcion i : REGISTRO) {
            if (i.alumno.getIdAlumno().equals(idAlumno) && i.taller.getIdTaller() == idTaller) {
                return true;
            }
        }
        return false;
    }

    /** Operación de clase: crea la inscripción y descuenta el lugar del taller. */
    public static Inscripcion registrar(Alumno alumno, Taller taller) {
        Inscripcion nueva = new Inscripcion(alumno, taller);
        taller.ocuparLugar();
        REGISTRO.add(nueva);
        return nueva;
    }

    public Alumno getAlumno() { return alumno; }
    public Taller getTaller() { return taller; }
    public LocalDateTime getFechaInscripcion() { return fechaInscripcion; }
    public String getEstatus() { return estatus; }
}
