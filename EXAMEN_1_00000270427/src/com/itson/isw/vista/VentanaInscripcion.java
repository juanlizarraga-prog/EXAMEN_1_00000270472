package com.itson.isw.vista;

import com.itson.isw.controlador.ControladorInscripcion;
import com.itson.isw.modelo.Alumno;
import com.itson.isw.modelo.Taller;
import com.itson.isw.modelo.Ticket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Vista: patrón Maestro-Detalle.
 * Izquierda: JList con los talleres. Derecha: detalle del taller y,
 * debajo, la zona que alterna entre captura del alumno y ticket.
 *
 * La vista solo habla con el controlador. Los objetos del modelo que recibe
 * se usan únicamente para LEER sus datos (getters).
 */
public class VentanaInscripcion extends JFrame {

    private static final String CARD_VACIO = "vacio";
    private static final String CARD_DETALLE = "detalle";
    private static final String CARD_ID = "identificacion";
    private static final String CARD_TICKET = "ticket";

    private ControladorInscripcion controlador;

    // Maestro
    private final DefaultListModel<Taller> modeloLista = new DefaultListModel<>();
    private final JList<Taller> listaTalleres = new JList<>(modeloLista);

    // Contenedores
    private final JPanel panelDerecho = new JPanel(new CardLayout());   // Estado 1 <-> 2
    private final JPanel panelInferior = new JPanel(new CardLayout());  // Estado 3 <-> 4

    // Detalle del taller
    private final JLabel lblNombre = new JLabel();
    private final JLabel lblInstructor = new JLabel();
    private final JLabel lblFecha = new JLabel();
    private final JLabel lblHorario = new JLabel();
    private final JLabel lblAula = new JLabel();
    private final JLabel lblCupo = new JLabel();
    private final JButton btnInscribirse = new JButton("Inscribirse");

    // Identificación del alumno
    private final JTextField txtIdAlumno = new JTextField(15);
    private final JButton btnBuscar = new JButton("Buscar");
    private final JLabel lblAlumnoId = new JLabel("-");
    private final JLabel lblAlumnoNombre = new JLabel("-");
    private final JLabel lblAlumnoSemestre = new JLabel("-");
    private final JLabel lblAlumnoPrograma = new JLabel("-");
    private final JButton btnConfirmar = new JButton("Confirmar Inscripción");
    private final JLabel lblMensaje = new JLabel(" ");

    // Ticket
    private final JTextArea areaTicket = new JTextArea();

    public VentanaInscripcion() {
        super("Sistema de Inscripción - Semana ISW");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        construirUI();
        configurarEventos();
    }

    public void setControlador(ControladorInscripcion controlador) {
        this.controlador = controlador;
    }

    // =====================================================================
    //  Construcción de la interfaz
    // =====================================================================

    private void construirUI() {
        listaTalleres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTalleres.setFont(listaTalleres.getFont().deriveFont(14f));
        JScrollPane scrollLista = new JScrollPane(listaTalleres);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Talleres disponibles"));
        scrollLista.setPreferredSize(new Dimension(280, 0));

        JPanel vacio = new JPanel();   // Estado 1: panel en blanco
        vacio.setBackground(Color.WHITE);

        JPanel detalle = new JPanel(new BorderLayout(10, 10));
        detalle.setBorder(new EmptyBorder(10, 10, 10, 10));
        detalle.add(construirPanelDatosTaller(), BorderLayout.NORTH);
        detalle.add(panelInferior, BorderLayout.CENTER);

        panelInferior.add(construirZonaIdentificacion(), CARD_ID);
        panelInferior.add(construirZonaTicket(), CARD_TICKET);
        panelInferior.setVisible(false);

        panelDerecho.add(vacio, CARD_VACIO);
        panelDerecho.add(detalle, CARD_DETALLE);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, panelDerecho);
        split.setDividerLocation(280);
        setContentPane(split);
    }

    private JPanel construirPanelDatosTaller() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Detalles del taller"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;

        String[] etiquetas = {"Taller:", "Instructor:", "Fecha:", "Horario:", "Aula:", "Lugares disponibles:"};
        JLabel[] valores = {lblNombre, lblInstructor, lblFecha, lblHorario, lblAula, lblCupo};
        for (int i = 0; i < etiquetas.length; i++) {
            g.gridx = 0;
            g.gridy = i;
            JLabel e = new JLabel(etiquetas[i]);
            e.setFont(e.getFont().deriveFont(Font.BOLD));
            p.add(e, g);
            g.gridx = 1;
            p.add(valores[i], g);
        }
        g.gridx = 0;
        g.gridy = etiquetas.length;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        p.add(btnInscribirse, g);
        return p;
    }

    private JPanel construirZonaIdentificacion() {
        JPanel zona = new JPanel(new BorderLayout(8, 8));

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.add(new JLabel("ID del alumno:"));
        fila.add(txtIdAlumno);
        fila.add(btnBuscar);

        JPanel datos = new JPanel(new GridLayout(4, 2, 6, 6));
        datos.setBorder(BorderFactory.createTitledBorder("Datos del alumno"));
        datos.add(new JLabel("ID:"));       datos.add(lblAlumnoId);
        datos.add(new JLabel("Nombre:"));   datos.add(lblAlumnoNombre);
        datos.add(new JLabel("Semestre:")); datos.add(lblAlumnoSemestre);
        datos.add(new JLabel("Programa:")); datos.add(lblAlumnoPrograma);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(lblMensaje, BorderLayout.CENTER);
        sur.add(btnConfirmar, BorderLayout.EAST);

        zona.add(fila, BorderLayout.NORTH);
        zona.add(datos, BorderLayout.CENTER);
        zona.add(sur, BorderLayout.SOUTH);
        return zona;
    }

    private JScrollPane construirZonaTicket() {
        areaTicket.setEditable(false);
        areaTicket.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        areaTicket.setBackground(new Color(255, 253, 231));
        JScrollPane sp = new JScrollPane(areaTicket);
        sp.setBorder(BorderFactory.createTitledBorder("Ticket de inscripción"));
        return sp;
    }

    // =====================================================================
    //  Eventos del usuario (se delegan al controlador)
    // =====================================================================

    private void configurarEventos() {
        listaTalleres.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                alSeleccionarTaller();
            }
        });
        btnInscribirse.addActionListener(e -> alPresionarInscribirse());
        btnBuscar.addActionListener(e -> alIngresarId());
        txtIdAlumno.addActionListener(e -> alIngresarId());   // Enter
        btnConfirmar.addActionListener(e -> alConfirmar());
    }

    private void alSeleccionarTaller() {
        Taller seleccionado = listaTalleres.getSelectedValue();
        if (seleccionado == null) {
            return;
        }
        panelInferior.setVisible(false);   // reinicia el flujo si cambia de taller
        controlador.seleccionarTaller(seleccionado.getIdTaller());
    }

    private void alPresionarInscribirse() {
        limpiarDatosAlumno();
        solicitarIdAlumno();
    }

    private void alIngresarId() {
        String id = txtIdAlumno.getText().trim();
        if (id.isEmpty()) {
            mostrarMensaje("Ingrese el ID del alumno.", true);
            return;
        }
        controlador.identificarAlumno(id);
    }

    private void alConfirmar() {
        controlador.confirmarInscripcion();
    }

    // =====================================================================
    //  Métodos que invoca el controlador
    // =====================================================================

    /** Estado 1. */
    public void mostrarTalleres(List<Taller> talleres) {
        modeloLista.clear();
        for (Taller t : talleres) {
            modeloLista.addElement(t);
        }
        ((CardLayout) panelDerecho.getLayout()).show(panelDerecho, CARD_VACIO);
    }

    /** Estado 2. */
    public void mostrarDetalleTaller(Taller t) {
        lblNombre.setText(t.getNombre());
        lblInstructor.setText(t.getInstructor());
        lblFecha.setText(t.getFecha());
        lblHorario.setText(t.getHorario());
        lblAula.setText(t.getAula());
        actualizarCupo(t);
        btnInscribirse.setEnabled(true);
        panelInferior.setVisible(false);
        ((CardLayout) panelDerecho.getLayout()).show(panelDerecho, CARD_DETALLE);
    }

    public void actualizarCupo(Taller t) {
        lblCupo.setText(t.getCupoDisponible() + " de " + t.getCupo());
    }

    /** Estado 3 (parte 1): muestra el campo para capturar el ID. */
    public void solicitarIdAlumno() {
        panelInferior.setVisible(true);
        ((CardLayout) panelInferior.getLayout()).show(panelInferior, CARD_ID);
        txtIdAlumno.requestFocusInWindow();
    }

    /** Estado 3 (parte 2): muestra los datos del alumno y habilita confirmar. */
    public void mostrarDatosAlumno(Alumno a) {
        lblAlumnoId.setText(a.getIdAlumno());
        lblAlumnoNombre.setText(a.getNombre());
        lblAlumnoSemestre.setText(String.valueOf(a.getSemestre()));
        lblAlumnoPrograma.setText(a.getPrograma());
        btnConfirmar.setEnabled(true);
        mostrarMensaje("Alumno encontrado. Confirme la inscripción.", false);
    }

    /** Estado 4: reemplaza la zona del alumno por el ticket. */
    public void mostrarTicket(Ticket t) {
        areaTicket.setText(formatearTicket(t));
        areaTicket.setCaretPosition(0);
        panelInferior.setVisible(true);
        ((CardLayout) panelInferior.getLayout()).show(panelInferior, CARD_TICKET);
    }

    public void mostrarMensaje(String texto, boolean esError) {
        lblMensaje.setForeground(esError ? new Color(180, 30, 30) : new Color(20, 120, 40));
        lblMensaje.setText(texto);
    }

    public void limpiarDatosAlumno() {
        txtIdAlumno.setText("");
        lblAlumnoId.setText("-");
        lblAlumnoNombre.setText("-");
        lblAlumnoSemestre.setText("-");
        lblAlumnoPrograma.setText("-");
        btnConfirmar.setEnabled(false);
        mostrarMensaje(" ", false);
    }

    private String formatearTicket(Ticket t) {
        return String.join("\n",
                "==================================",
                "     TICKET DE INSCRIPCIÓN",
                "        SEMANA DE ISW",
                "==================================",
                "Folio:      " + t.getFolio(),
                "Estatus:    " + t.getEstatus(),
                "Emitido:    " + t.getFechaEmision(),
                "----------------------------------",
                "Alumno:     " + t.getAlumno().getNombre(),
                "ID:         " + t.getAlumno().getIdAlumno(),
                "Semestre:   " + t.getAlumno().getSemestre(),
                "Programa:   " + t.getAlumno().getPrograma(),
                "----------------------------------",
                "Taller:     " + t.getTaller().getNombre(),
                "Instructor: " + t.getTaller().getInstructor(),
                "Fecha:      " + t.getTaller().getFecha(),
                "Horario:    " + t.getTaller().getHorario(),
                "Aula:       " + t.getTaller().getAula(),
                "==================================");
    }
}
