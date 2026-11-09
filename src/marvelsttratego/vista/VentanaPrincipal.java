package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import marvelsttratego.control.GestorPartidas;
import marvelsttratego.control.GestorUsuarios;
import marvelsttratego.control.MotorJuego;
import marvelsttratego.modelo.Jugador;
import marvelsttratego.modelo.Partida;

public class VentanaPrincipal extends JFrame {

    private final GestorUsuarios gestorUsuarios;
    private final GestorPartidas gestorPartidas;
    private Jugador usuarioActual;

    private final CardLayout cartas = new CardLayout();
    private final JPanel contenedor = new JPanel(cartas);
    private final JTextArea areaMensajes = new JTextArea(3, 60);

    private final PanelLogin panelLogin;
    private final PanelCrear panelCrear;
    private final PanelMenu panelMenu;
    private final PanelPartida panelPartida;

    public VentanaPrincipal(GestorUsuarios gestorUsuarios, GestorPartidas gestorPartidas) {
        super("Stratego - Marvel Heroes");
        this.gestorUsuarios = gestorUsuarios;
        this.gestorPartidas = gestorPartidas;

        panelLogin = new PanelLogin(this);
        panelCrear = new PanelCrear(this);
        panelMenu = new PanelMenu(this);
        panelPartida = new PanelPartida(this);

        contenedor.add(new PanelInicio(this), "inicio");
        contenedor.add(panelLogin, "login");
        contenedor.add(panelCrear, "crear");
        contenedor.add(panelMenu, "menu");
        contenedor.add(panelPartida, "partida");

        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        areaMensajes.setMargin(new Insets(8, 12, 8, 12));
        areaMensajes.setBackground(new Color(25, 30, 55));
        areaMensajes.setForeground(Color.WHITE);

        add(contenedor, BorderLayout.CENTER);
        add(new JScrollPane(areaMensajes), BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        abrirInicio();
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public GestorPartidas getGestorPartidas() {
        return gestorPartidas;
    }

    public Jugador getUsuarioActual() {
        return usuarioActual;
    }

    public void mostrarMensaje(String mensaje) {
        areaMensajes.setText(mensaje);
    }

    public void agregarMensaje(String mensaje) {
        areaMensajes.append("\n" + mensaje);
    }

    public void abrirInicio() {
        cartas.show(contenedor, "inicio");
    }

    public void abrirLogin() {
        panelLogin.limpiar();
        cartas.show(contenedor, "login");
    }

    public void abrirCrear() {
        panelCrear.limpiar();
        cartas.show(contenedor, "crear");
    }

    public void iniciarSesion(Jugador jugador) {
        usuarioActual = jugador;
        panelMenu.entrar();
        cartas.show(contenedor, "menu");
    }

    public void cerrarSesion() {
        usuarioActual = null;
        cartas.show(contenedor, "inicio");
    }

    public void mostrarPartida(Partida partida, MotorJuego motor) {
        panelPartida.iniciar(partida, motor);
        cartas.show(contenedor, "partida");
    }

    public void volverAlMenu() {
        panelMenu.entrar();
        cartas.show(contenedor, "menu");
    }
}
