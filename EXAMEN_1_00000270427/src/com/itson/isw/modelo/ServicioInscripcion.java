package com.itson.isw.modelo;

import java.util.List;

/**
 * Modelo (capa de servicio): coordina las entidades y aplica las reglas
 * de negocio. No guarda datos propios: se los pide a Taller, Alumno,
 * Inscripcion y Ticket.
 */
public class ServicioInscripcion {

    /** Devuelve los talleres que se muestran en el JList. */
    public List<Taller> obtenerTalleresDisponibles() {
        return Taller.consultarTalleres();
    }

    public Taller obtenerDetalleTaller(int idTaller) throws InscripcionException {
        return Taller.consultarDetalle(idTaller)
                .orElseThrow(() -> new InscripcionException("El taller seleccionado no existe."));
    }

    /**
     * Valida que el alumno exista, que no esté ya inscrito y que el taller
     * tenga cupo. Devuelve los datos del alumno si todo es correcto.
     */
    public Alumno validarAlumno(String idAlumno, int idTaller) throws InscripcionException {
        Taller taller = obtenerDetalleTaller(idTaller);

        Alumno alumno = Alumno.consultarDatos(idAlumno)
                .orElseThrow(() -> new InscripcionException("No se encontró ningún alumno con ese ID."));

        if (Inscripcion.existeInscripcion(alumno.getIdAlumno(), idTaller)) {
            throw new InscripcionException("El alumno ya está inscrito en este taller.");
        }
        if (!taller.verificarCupo()) {
            throw new InscripcionException("El taller ya no tiene cupo disponible.");
        }
        return alumno;
    }

    /** Registra la inscripción y genera el ticket. */
    public Ticket inscribir(String idAlumno, int idTaller) throws InscripcionException {
        Alumno alumno = validarAlumno(idAlumno, idTaller);
        Taller taller = obtenerDetalleTaller(idTaller);

        Inscripcion inscripcion = Inscripcion.registrar(alumno, taller);
        String folio = Ticket.generarFolio();
        return new Ticket(folio, inscripcion);
    }
}
