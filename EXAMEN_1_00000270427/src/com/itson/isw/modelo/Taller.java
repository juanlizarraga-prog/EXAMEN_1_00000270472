package com.itson.isw.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Modelo: taller de la Semana de ISW.
 * Los datos están simulados en memoria; cuando exista la BD, solo cambia
 * la implementación de consultarTalleres() y consultarDetalle().
 */
public class Taller {

    private static final List<Taller> TALLERES = new ArrayList<>();

    static {
        TALLERES.add(new Taller(1, "Taller de Pruebas de Software", "Dra. Ana López",
                "20/10/2026", "10:00 - 12:00", "Aula 301", 30));
        TALLERES.add(new Taller(2, "Taller de Arquitectura Hexagonal", "Mtro. Luis Peña",
                "21/10/2026", "12:00 - 14:00", "Aula 302", 25));
        TALLERES.add(new Taller(3, "Taller de DevOps con Docker", "Ing. Carla Ruiz",
                "22/10/2026", "09:00 - 11:00", "Laboratorio 2", 20));
    }

    private final int idTaller;
    private final String nombre;
    private final String instructor;
    private final String fecha;
    private final String horario;
    private final String aula;
    private final int cupo;
    private int inscritos;

    public Taller(int idTaller, String nombre, String instructor, String fecha,
                  String horario, String aula, int cupo) {
        this.idTaller = idTaller;
        this.nombre = nombre;
        this.instructor = instructor;
        this.fecha = fecha;
        this.horario = horario;
        this.aula = aula;
        this.cupo = cupo;
        this.inscritos = 0;
    }

    /** Operación de clase: devuelve todos los talleres registrados. */
    public static List<Taller> consultarTalleres() {
        return Collections.unmodifiableList(TALLERES);
    }

    /** Operación de clase: busca un taller por su ID. */
    public static Optional<Taller> consultarDetalle(int idTaller) {
        for (Taller t : TALLERES) {
            if (t.idTaller == idTaller) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    /** @return true si todavía quedan lugares disponibles. */
    public boolean verificarCupo() {
        return inscritos < cupo;
    }

    /** Descuenta un lugar al registrarse una inscripción. */
    public void ocuparLugar() {
        if (!verificarCupo()) {
            throw new IllegalStateException("El taller no tiene cupo disponible.");
        }
        inscritos++;
    }

    public int getIdTaller() { return idTaller; }
    public String getNombre() { return nombre; }
    public String getInstructor() { return instructor; }
    public String getFecha() { return fecha; }
    public String getHorario() { return horario; }
    public String getAula() { return aula; }
    public int getCupo() { return cupo; }
    public int getCupoDisponible() { return cupo - inscritos; }

    /** Lo que muestra el JList. */
    @Override
    public String toString() {
        return nombre;
    }
}
