package marvelsttratego.vista;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import marvelsttratego.excepciones.CredencialesInvalidasException;
import marvelsttratego.modelo.Jugador;

public class PanelLogin extends PanelFondo implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JTextField campoUsuario = new JTextField(15);
    private final JPasswordField campoPassword = new JPasswordField(15);
    private final JButton botonEntrar = new JButton("ENTRAR");
    private final JButton botonVolver = new JButton("VOLVER");

    public PanelLogin(VentanaPrincipal ventana) {
        super("fondo_login.jpg");
        this.ventana = ventana;

        PanelCristal cristal = new PanelCristal();
        cristal.setLayout(new GridLayout(0, 2, 10, 10));
        cristal.add(new JLabel("Username:"));
        cristal.add(campoUsuario);
        cristal.add(new JLabel("Password:"));
        cristal.add(campoPassword);
        cristal.add(botonEntrar);
        cristal.add(botonVolver);
        agregarAbajo(cristal);

        botonEntrar.addActionListener(this);
        botonVolver.addActionListener(this);
        campoPassword.addActionListener(this);
    }

    public void limpiar() {
        campoUsuario.setText("");
        campoPassword.setText("");
        campoUsuario.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVolver) {
            ventana.mostrarMensaje("");
            ventana.abrirInicio();
        } else {
            entrar();
        }
    }

    private void entrar() {
        String username = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword());
        try {
            Jugador jugador = ventana.getGestorUsuarios().login(username, password);
            ventana.mostrarMensaje("");
            ventana.iniciarSesion(jugador);
        } catch (CredencialesInvalidasException ex) {
            ventana.mostrarMensaje(ex.getMessage() + " Intente de nuevo.");
            campoPassword.setText("");
        }
    }
}
