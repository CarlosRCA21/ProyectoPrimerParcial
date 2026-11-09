package marvelsttratego.vista;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import marvelsttratego.excepciones.CredencialesInvalidasException;

public class PanelEliminarCuenta extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JPasswordField campoPassword = new JPasswordField(15);
    private final JButton botonEliminar = new JButton("ELIMINAR MI CUENTA");

    public PanelEliminarCuenta(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());

        JPanel formulario = new JPanel(new GridLayout(0, 1, 10, 10));
        formulario.add(new JLabel("Para eliminar su cuenta confirme con su password:"));
        formulario.add(campoPassword);
        formulario.add(botonEliminar);
        add(formulario);

        botonEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String password = new String(campoPassword.getPassword());
        try {
            ventana.getGestorUsuarios().eliminar(ventana.getUsuarioActual(), password);
            campoPassword.setText("");
            ventana.cerrarSesion();
            ventana.mostrarMensaje("Su cuenta fue eliminada.");
        } catch (CredencialesInvalidasException ex) {
            ventana.mostrarMensaje(ex.getMessage() + " La cuenta no fue eliminada.");
        }
    }
}
