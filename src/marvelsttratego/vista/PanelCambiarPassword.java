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
import marvelsttratego.excepciones.PasswordInvalidoException;

public class PanelCambiarPassword extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JPasswordField campoActual = new JPasswordField(15);
    private final JPasswordField campoNuevo = new JPasswordField(15);
    private final JButton botonCambiar = new JButton("CAMBIAR PASSWORD");

    public PanelCambiarPassword(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.add(new JLabel("Password actual:"));
        formulario.add(campoActual);
        formulario.add(new JLabel("Password nuevo (5 caracteres):"));
        formulario.add(campoNuevo);
        formulario.add(new JLabel(""));
        formulario.add(botonCambiar);
        add(formulario);

        botonCambiar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actual = new String(campoActual.getPassword());
        String nuevo = new String(campoNuevo.getPassword());
        try {
            ventana.getUsuarioActual().cambiarPassword(actual, nuevo);
            ventana.mostrarMensaje("El password fue cambiado correctamente.");
            campoActual.setText("");
            campoNuevo.setText("");
        } catch (CredencialesInvalidasException ex) {
            ventana.mostrarMensaje(ex.getMessage());
        } catch (PasswordInvalidoException ex) {
            ventana.mostrarMensaje(ex.getMessage());
        }
    }
}
