package marvelsttratego.vista;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelCerrarSesion extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final PanelMenu menu;
    private final JButton botonSi = new JButton("SÍ, CERRAR SESIÓN");
    private final JButton botonNo = new JButton("NO, VOLVER");

    public PanelCerrarSesion(VentanaPrincipal ventana, PanelMenu menu) {
        this.ventana = ventana;
        this.menu = menu;
        setLayout(new GridBagLayout());

        JPanel formulario = new JPanel(new GridLayout(0, 1, 10, 10));
        formulario.add(new JLabel("¿Seguro que desea cerrar sesión?"));
        formulario.add(botonSi);
        formulario.add(botonNo);
        add(formulario);

        botonSi.addActionListener(this);
        botonNo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSi) {
            ventana.cerrarSesion();
            ventana.mostrarMensaje("Sesión cerrada.");
        } else {
            menu.mostrarSeccion("bienvenida");
        }
    }
}
