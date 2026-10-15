package com.itson.isw.modelo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Modelo: alumno que se inscribe a un taller.
 * Datos simulados en memoria (reemplazar por BD cuando exista).
 */
public class Alumno {

    private static final Map<String, Alumno> ALUMNOS = new LinkedHashMap<>();

    static {
        registrar(new Alumno("00000123456", "María Fernanda Soto", 5, "Ing. en Software"));
        registrar(new Alumno("00000654321", "José Carlos Ramírez", 7, "Ing. en Software"));
        registrar(new Alumno("00000111222", "Ana Lucía Torres", 3, "Ing. en Software"));
    }

    private final String idAlumno;
    private final String nombre;
    private final int semestre;
    private final String programa;

    public Alumno(String idAlumno, String nombre, int semestre, String programa) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.semestre = semestre;
        this.programa = programa;
    }

    private static void registrar(Alumno alumno) {
        ALUMNOS.put(alumno.idAlumno, alumno);
    }

    /** Operación de clase: busca un alumno por su ID. */
    public static Optional<Alumno> consultarDatos(String idAlumno) {
        if (idAlumno == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ALUMNOS.get(idAlumno.trim()));
    }

    public String getIdAlumno() { return idAlumno; }
    public String getNombre() { return nombre; }
    public int getSemestre() { return semestre; }
    public String getPrograma() { return programa; }
}
