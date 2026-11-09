package marvelsttratego.vista;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import marvelsttratego.control.GestorUsuarios;
import marvelsttratego.control.MotorJuego;
import marvelsttratego.excepciones.MovimientoInvalidoException;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Jugador;
import marvelsttratego.modelo.ModoJuego;
import marvelsttratego.modelo.Partida;

public class PanelPartidaNueva extends JPanel implements Actualizable, ActionListener {

    private final VentanaPrincipal ventana;
    private final JComboBox<String> comboOponente = new JComboBox<>();
    private final JComboBox<String> comboBando = new JComboBox<>();
    private final JComboBox<String> comboModo = new JComboBox<>();
    private final JButton botonIniciar = new JButton("INICIAR PARTIDA");
    private final JLabel etiquetaAviso = new JLabel("");
    private Jugador[] oponentes = new Jugador[0];

    public PanelPartidaNueva(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());

        for (Bando bando : Bando.values()) {
            comboBando.addItem(bando.getNombre());
        }
        for (ModoJuego modo : ModoJuego.values()) {
            comboModo.addItem(modo.getNombre());
        }

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.add(new JLabel("Oponente:"));
        formulario.add(comboOponente);
        formulario.add(new JLabel("Mi bando:"));
        formulario.add(comboBando);
        formulario.add(new JLabel("Modo de juego:"));
        formulario.add(comboModo);
        formulario.add(etiquetaAviso);
        formulario.add(botonIniciar);
        add(formulario);

        botonIniciar.addActionListener(this);
    }

    @Override
    public void actualizar() {
        GestorUsuarios gestor = ventana.getGestorUsuarios();
        comboOponente.removeAllItems();
        oponentes = new Jugador[gestor.getCantidad()];
        int total = 0;
        for (int i = 0; i < gestor.getCantidad(); i++) {
            Jugador jugador = gestor.getUsuario(i);
            if (jugador != ventana.getUsuarioActual()) {
                oponentes[total] = jugador;
                comboOponente.addItem(jugador.getUsername());
                total++;
            }
        }
        botonIniciar.setEnabled(total > 0);
        if (total > 0) {
            etiquetaAviso.setText("");
        } else {
            etiquetaAviso.setText("Se necesita otro jugador.");
            ventana.mostrarMensaje("Partida Nueva no está disponible: solo hay un jugador registrado.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int posicion = comboOponente.getSelectedIndex();
        if (posicion < 0) {
            return;
        }
        Jugador yo = ventana.getUsuarioActual();
        Jugador rival = oponentes[posicion];
        Bando miBando = Bando.values()[comboBando.getSelectedIndex()];
        ModoJuego modo = ModoJuego.values()[comboModo.getSelectedIndex()];

        Partida partida;
        if (miBando == Bando.HEROES) {
            partida = new Partida(yo, rival, modo);
        } else {
            partida = new Partida(rival, yo, modo);
        }
        MotorJuego motor = new MotorJuego(partida, ventana.getGestorPartidas());
        try {
            motor.prepararTablero();
            ventana.mostrarMensaje("");
            ventana.mostrarPartida(partida, motor);
        } catch (MovimientoInvalidoException ex) {
            ventana.mostrarMensaje("No se pudo preparar el tablero: " + ex.getMessage());
        }
    }
}
