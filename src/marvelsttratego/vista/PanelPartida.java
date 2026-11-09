package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import marvelsttratego.control.MotorJuego;
import marvelsttratego.excepciones.MovimientoInvalidoException;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Partida;
import marvelsttratego.modelo.Pieza;
import marvelsttratego.modelo.Posicion;
import marvelsttratego.modelo.Tablero;
import marvelsttratego.modelo.TipoPieza;

public class PanelPartida extends JPanel implements ActionListener {

    private static final int TAMANO_ICONO = 44;

    private final VentanaPrincipal ventana;
    private Partida partida;
    private MotorJuego motor;
    private Posicion seleccionada;

    private final PanelTablero panelTablero = new PanelTablero(this);
    private final JLabel etiquetaTurno = new JLabel("", SwingConstants.CENTER);
    private final JLabel etiquetaJugadores = new JLabel("", SwingConstants.CENTER);
    private final JLabel tituloHeroes = new JLabel("", SwingConstants.CENTER);
    private final JLabel tituloVillanos = new JLabel("", SwingConstants.CENTER);
    private final JPanel listaHeroes = new JPanel();
    private final JPanel listaVillanos = new JPanel();
    private final JButton botonRetiro = new JButton("RETIRO");
    private final JButton botonConfirmar = new JButton("CONFIRMAR RETIRO");
    private final JButton botonCancelar = new JButton("CANCELAR");
    private final JButton botonVolver = new JButton("VOLVER AL MENÚ");

    public PanelPartida(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());

        etiquetaTurno.setFont(new Font("SansSerif", Font.BOLD, 26));
        etiquetaTurno.setPreferredSize(new Dimension(700, 40));

        JPanel filaTurno = new JPanel();
        filaTurno.add(etiquetaTurno);
        filaTurno.add(botonRetiro);
        filaTurno.add(botonConfirmar);
        filaTurno.add(botonCancelar);
        filaTurno.add(botonVolver);

        JPanel norte = new JPanel(new GridLayout(2, 1));
        norte.add(filaTurno);
        norte.add(etiquetaJugadores);
        add(norte, BorderLayout.NORTH);

        add(panelTablero, BorderLayout.CENTER);
        add(crearPanelVencidas(tituloHeroes, listaHeroes), BorderLayout.WEST);
        add(crearPanelVencidas(tituloVillanos, listaVillanos), BorderLayout.EAST);

        botonRetiro.addActionListener(this);
        botonConfirmar.addActionListener(this);
        botonCancelar.addActionListener(this);
        botonVolver.addActionListener(this);
    }

    private JPanel crearPanelVencidas(JLabel titulo, JPanel lista) {
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);
        return panel;
    }

    public void iniciar(Partida partida, MotorJuego motor) {
        this.partida = partida;
        this.motor = motor;
        this.seleccionada = null;
        botonRetiro.setVisible(true);
        botonConfirmar.setVisible(false);
        botonCancelar.setVisible(false);
        botonVolver.setVisible(false);
        etiquetaJugadores.setText("Héroes: " + partida.getJugador(Bando.HEROES).getUsername()
                + "   |   Villanos: " + partida.getJugador(Bando.VILLANOS).getUsername()
                + "   |   Modo: " + partida.getModo().getNombre());
        ventana.mostrarMensaje("Partida iniciada. Seleccione una pieza propia y luego la casilla de destino.");
        refrescar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen == botonRetiro) {
            pedirConfirmacion();
        } else if (origen == botonConfirmar) {
            confirmarRetiro();
        } else if (origen == botonCancelar) {
            cancelarRetiro();
        } else if (origen == botonVolver) {
            ventana.volverAlMenu();
        } else {
            for (int fila = 0; fila < Tablero.FILAS; fila++) {
                for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
                    if (origen == panelTablero.getCasilla(fila, columna)) {
                        alHacerClic(fila, columna);
                    }
                }
            }
        }
    }

    private void refrescar() {
        Tablero tablero = partida.getTablero();
        Bando turno = partida.getTurno();
        for (int fila = 0; fila < Tablero.FILAS; fila++) {
            for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
                Posicion posicion = new Posicion(fila, columna);
                Pieza pieza = tablero.getPieza(posicion);
                BufferedImage imagen = null;
                String respaldo = "";
                String ayuda = null;
                if (pieza != null) {
                    if (esVisible(pieza, turno)) {
                        imagen = GestorImagenes.getImagenPieza(pieza);
                        ayuda = MotorJuego.describir(pieza);
                    } else {
                        imagen = GestorImagenes.getReverso(pieza.getBando());
                    }
                    if (imagen == null) {
                        respaldo = textoCelda(pieza, turno);
                    }
                }
                Casilla casilla = panelTablero.getCasilla(fila, columna);
                casilla.mostrar(imagen, respaldo, posicion.equals(seleccionada));
                casilla.setToolTipText(ayuda);
            }
        }

        if (partida.estaTerminada()) {
            etiquetaTurno.setText("Partida terminada");
            etiquetaTurno.setForeground(Color.DARK_GRAY);
        } else {
            etiquetaTurno.setText("Turno de: " + turno.getNombre() + " ("
                    + partida.getJugador(turno).getUsername() + ")");
            if (turno == Bando.HEROES) {
                etiquetaTurno.setForeground(new Color(25, 80, 170));
            } else {
                etiquetaTurno.setForeground(new Color(20, 110, 40));
            }
        }
        actualizarVencidas(Bando.HEROES, tituloHeroes, listaHeroes);
        actualizarVencidas(Bando.VILLANOS, tituloVillanos, listaVillanos);
    }

    private void actualizarVencidas(Bando bando, JLabel titulo, JPanel lista) {
        List<Pieza> vencidas = partida.getVencidas(bando);
        lista.removeAll();
        for (Pieza pieza : vencidas) {
            JLabel etiqueta = new JLabel(MotorJuego.describir(pieza));
            BufferedImage imagen = GestorImagenes.getImagenPieza(pieza);
            if (imagen != null) {
                Image reducida = imagen.getScaledInstance(TAMANO_ICONO, TAMANO_ICONO, Image.SCALE_SMOOTH);
                etiqueta.setIcon(new ImageIcon(reducida));
            }
            lista.add(etiqueta);
        }
        titulo.setText("Vencidos " + bando.getNombre() + ": " + vencidas.size());
        lista.revalidate();
        lista.repaint();
    }

    private boolean esVisible(Pieza pieza, Bando turno) {
        return partida.estaTerminada()
                || partida.getModo().mostrarTodasLasPiezas()
                || pieza.estaRevelada()
                || pieza.verificarBando(turno);
    }

    private String textoCelda(Pieza pieza, Bando turno) {
        String letra = "V";
        if (pieza.getBando() == Bando.HEROES) {
            letra = "H";
        }
        if (!esVisible(pieza, turno)) {
            return letra + "?";
        }
        if (pieza.getTipo() == TipoPieza.BOMBA) {
            return letra + "B";
        }
        if (pieza.getTipo() == TipoPieza.TIERRA) {
            return letra + "T";
        }
        return letra + pieza.getRango();
    }

    private void alHacerClic(int fila, int columna) {
        if (partida.estaTerminada()) {
            return;
        }
        Posicion destino = new Posicion(fila, columna);
        Pieza pieza = partida.getTablero().getPieza(destino);

        if (seleccionada == null) {
            elegir(destino, pieza);
            return;
        }
        if (destino.equals(seleccionada)) {
            seleccionada = null;
            ventana.mostrarMensaje("Selección cancelada.");
            refrescar();
            return;
        }
        if (pieza != null && pieza.verificarBando(partida.getTurno())) {
            elegir(destino, pieza);
            return;
        }

        try {
            String resultado = motor.mover(seleccionada, destino);
            seleccionada = null;
            ventana.mostrarMensaje(resultado);
        } catch (MovimientoInvalidoException ex) {
            ventana.mostrarMensaje(ex.getMessage());
        }
        refrescar();
        if (partida.estaTerminada()) {
            mostrarFin();
        }
    }

    private void elegir(Posicion posicion, Pieza pieza) {
        if (pieza == null || !pieza.verificarBando(partida.getTurno())) {
            ventana.mostrarMensaje("Seleccione una pieza propia.");
            return;
        }
        if (!pieza.puedeMoverse()) {
            ventana.mostrarMensaje("Las bombas y la tierra no se pueden mover.");
            return;
        }
        seleccionada = posicion;
        ventana.mostrarMensaje("Pieza seleccionada: " + MotorJuego.describir(pieza)
                + ". Ahora elija la casilla de destino.");
        refrescar();
    }

    private void pedirConfirmacion() {
        botonRetiro.setVisible(false);
        botonConfirmar.setVisible(true);
        botonCancelar.setVisible(true);
        ventana.mostrarMensaje("¿Seguro que desea retirarse? El oponente recibirá 3 puntos.");
    }

    private void cancelarRetiro() {
        botonRetiro.setVisible(true);
        botonConfirmar.setVisible(false);
        botonCancelar.setVisible(false);
        ventana.mostrarMensaje("Retiro cancelado.");
    }

    private void confirmarRetiro() {
        motor.retirarse();
        seleccionada = null;
        String registro = partida.generarRegistro().getMensaje();
        ventana.volverAlMenu();
        ventana.mostrarMensaje(registro);
    }

    private void mostrarFin() {
        botonRetiro.setVisible(false);
        botonConfirmar.setVisible(false);
        botonCancelar.setVisible(false);
        botonVolver.setVisible(true);
        ventana.agregarMensaje(partida.generarRegistro().getMensaje());
    }
}
