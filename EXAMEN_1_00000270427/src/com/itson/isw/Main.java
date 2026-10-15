package com.itson.isw;

import com.itson.isw.controlador.ControladorInscripcion;
import com.itson.isw.modelo.ServicioInscripcion;
import com.itson.isw.vista.VentanaInscripcion;

import javax.swing.SwingUtilities;

/** Punto de entrada: arma Vista, Modelo y Controlador. */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInscripcion vista = new VentanaInscripcion();
            ServicioInscripcion servicio = new ServicioInscripcion();
            ControladorInscripcion controlador = new ControladorInscripcion(vista, servicio);

            vista.setControlador(controlador);
            controlador.iniciar();
            vista.setVisible(true);
        });
    }
}
