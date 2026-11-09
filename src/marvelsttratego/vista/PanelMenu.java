package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelMenu extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final CardLayout cartas = new CardLayout();
    private final JPanel contenido = new JPanel(cartas);
    private final JLabel etiquetaBienvenida = new JLabel("", SwingConstants.CENTER);

    private final PanelPartidaNueva panelPartidaNueva;
    private final PanelPerfil panelPerfil;
    private final PanelRanking panelRanking;
    private final PanelBatallas panelBatallas;

    private final JButton botonPartida = new JButton("Partida Nueva");
    private final JButton botonLog = new JButton("Log de Mis Últimos Juegos");
    private final JButton botonPassword = new JButton("Cambiar mi Password");
    private final JButton botonEliminar = new JButton("Eliminar mi Cuenta");
    private final JButton botonRanking = new JButton("Ranking");
    private final JButton botonBatallas = new JButton("Batallas");
    private final JButton botonCerrar = new JButton("CERRAR SESIÓN");

    public PanelMenu(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());

        panelPartidaNueva = new PanelPartidaNueva(ventana);
        panelPerfil = new PanelPerfil(ventana);
        panelRanking = new PanelRanking(ventana);
        panelBatallas = new PanelBatallas(ventana);

        etiquetaBienvenida.setFont(new Font("SansSerif", Font.BOLD, 36));
        contenido.add(etiquetaBienvenida, "bienvenida");
        contenido.add(panelPartidaNueva, "partida");
        contenido.add(panelPerfil, "perfil");
        contenido.add(new PanelCambiarPassword(ventana), "password");
        contenido.add(new PanelEliminarCuenta(ventana), "eliminar");
        contenido.add(panelRanking, "ranking");
        contenido.add(panelBatallas, "batallas");
        contenido.add(new PanelCerrarSesion(ventana, this), "cerrar");

        JPanel navegacion = new JPanel(new GridLayout(0, 1, 8, 8));
        navegacion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        navegacion.add(crearTitulo("STRATEGO – MARVEL HEROES!"));
        navegacion.add(botonPartida);
        navegacion.add(crearTitulo("MI PERFIL"));
        navegacion.add(botonLog);
        navegacion.add(botonPassword);
        navegacion.add(botonEliminar);
        navegacion.add(crearTitulo("UNIVERSO MARVEL"));
        navegacion.add(botonRanking);
        navegacion.add(botonBatallas);
        navegacion.add(botonCerrar);

        JPanel izquierda = new JPanel(new BorderLayout());
        izquierda.add(navegacion, BorderLayout.NORTH);
        add(izquierda, BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);

        botonPartida.addActionListener(this);
        botonLog.addActionListener(this);
        botonPassword.addActionListener(this);
        botonEliminar.addActionListener(this);
        botonRanking.addActionListener(this);
        botonBatallas.addActionListener(this);
        botonCerrar.addActionListener(this);
    }

    private JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        return titulo;
    }

    public void entrar() {
        etiquetaBienvenida.setText("Bienvenido, " + ventana.getUsuarioActual().getUsername());
        botonPartida.setEnabled(ventana.getGestorUsuarios().getCantidad() > 1);
        mostrarSeccion("bienvenida");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen == botonPartida) {
            abrir(panelPartidaNueva, "partida");
        } else if (origen == botonLog) {
            abrir(panelPerfil, "perfil");
        } else if (origen == botonPassword) {
            mostrarSeccion("password");
        } else if (origen == botonEliminar) {
            mostrarSeccion("eliminar");
        } else if (origen == botonRanking) {
            abrir(panelRanking, "ranking");
        } else if (origen == botonBatallas) {
            abrir(panelBatallas, "batallas");
        } else if (origen == botonCerrar) {
            ventana.mostrarMensaje("");
            mostrarSeccion("cerrar");
        }
    }

    private void abrir(Actualizable panel, String nombre) {
        panel.actualizar();
        mostrarSeccion(nombre);
    }

    public void mostrarSeccion(String nombre) {
        cartas.show(contenido, nombre);
    }
}
