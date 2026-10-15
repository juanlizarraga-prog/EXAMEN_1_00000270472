package com.itson.isw.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo: comprobante de una inscripción confirmada.
 */
public class Ticket {

    private static int consecutivo = 1;

    private final String folio;
    private final String fechaEmision;
    private final String estatus;
    private final Inscripcion inscripcion;

    public Ticket(String folio, Inscripcion inscripcion) {
        this.folio = folio;
        this.inscripcion = inscripcion;
        this.fechaEmision = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.estatus = inscripcion.getEstatus();
    }

    /** Operación de clase: genera el siguiente folio (ISW-0001, ISW-0002...). */
    public static String generarFolio() {
        return String.format("ISW-%04d", consecutivo++);
    }

    public String getFolio() { return folio; }
    public String getFechaEmision() { return fechaEmision; }
    public String getEstatus() { return estatus; }
    public Inscripcion getInscripcion() { return inscripcion; }
    public Alumno getAlumno() { return inscripcion.getAlumno(); }
    public Taller getTaller() { return inscripcion.getTaller(); }
}
