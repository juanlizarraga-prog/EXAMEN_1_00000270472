package com.itson.isw.controlador;

import com.itson.isw.modelo.Alumno;
import com.itson.isw.modelo.InscripcionException;
import com.itson.isw.modelo.ServicioInscripcion;
import com.itson.isw.modelo.Taller;
import com.itson.isw.modelo.Ticket;
import com.itson.isw.vista.VentanaInscripcion;

/**
 * Controlador: recibe los eventos de la vista, invoca al servicio del
 * modelo y le indica a la vista qué mostrar. No contiene reglas de negocio.
 */
public class ControladorInscripcion {

    private final VentanaInscripcion vista;
    private final ServicioInscripcion servicio;

    // Estado de la transacción en curso
    private Integer idTallerSeleccionado;
    private String idAlumnoActual;

    public ControladorInscripcion(VentanaInscripcion vista, ServicioInscripcion servicio) {
        this.vista = vista;
        this.servicio = servicio;
    }

    /** Estado 1: carga la lista de talleres. */
    public void iniciar() {
        vista.mostrarTalleres(servicio.obtenerTalleresDisponibles());
    }

    /** Estado 2: carga los detalles del taller elegido. */
    public void seleccionarTaller(int idTaller) {
        try {
            Taller taller = servicio.obtenerDetalleTaller(idTaller);
            idTallerSeleccionado = idTaller;
            idAlumnoActual = null;
            vista.mostrarDetalleTaller(taller);
        } catch (InscripcionException e) {
            vista.mostrarMensaje(e.getMessage(), true);
        }
    }

    /** Estado 3: valida el ID del alumno y muestra sus datos. */
    public void identificarAlumno(String idAlumno) {
        if (idTallerSeleccionado == null) {
            vista.mostrarMensaje("Seleccione primero un taller.", true);
            return;
        }
        try {
            Alumno alumno = servicio.validarAlumno(idAlumno, idTallerSeleccionado);
            idAlumnoActual = alumno.getIdAlumno();
            vista.mostrarDatosAlumno(alumno);
        } catch (InscripcionException e) {
            idAlumnoActual = null;
            vista.limpiarDatosAlumno();
            vista.mostrarMensaje(e.getMessage(), true);
        }
    }

    /** Estado 4: registra la inscripción y muestra el ticket. */
    public void confirmarInscripcion() {
        if (idTallerSeleccionado == null || idAlumnoActual == null) {
            vista.mostrarMensaje("Identifique primero al alumno.", true);
            return;
        }
        try {
            Ticket ticket = servicio.inscribir(idAlumnoActual, idTallerSeleccionado);
            idAlumnoActual = null;
            vista.mostrarTicket(ticket);
            // Se refresca el detalle para que el cupo disponible quede actualizado
            vista.actualizarCupo(ticket.getTaller());
        } catch (InscripcionException e) {
            vista.mostrarMensaje(e.getMessage(), true);
        }
    }
}
