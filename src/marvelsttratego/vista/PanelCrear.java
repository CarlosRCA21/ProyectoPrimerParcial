package marvelsttratego.vista;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import marvelsttratego.excepciones.PasswordInvalidoException;
import marvelsttratego.excepciones.UsuarioDuplicadoException;

public class PanelCrear extends PanelFondo implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JTextField campoUsuario = new JTextField(15);
    private final JPasswordField campoPassword = new JPasswordField(15);
    private final JButton botonCrear = new JButton("CREAR");
    private final JButton botonCancelar = new JButton("CANCELAR");

    public PanelCrear(VentanaPrincipal ventana) {
        super("fondo_login.jpg");
        this.ventana = ventana;

        PanelCristal cristal = new PanelCristal();
        cristal.setLayout(new GridLayout(0, 2, 10, 10));
        cristal.add(new JLabel("Username:"));
        cristal.add(campoUsuario);
        cristal.add(new JLabel("Password (5 caracteres):"));
        cristal.add(campoPassword);
        cristal.add(botonCrear);
        cristal.add(botonCancelar);
        agregarAbajo(cristal);

        botonCrear.addActionListener(this);
        botonCancelar.addActionListener(this);
        campoPassword.addActionListener(this);
    }

    public void limpiar() {
        campoUsuario.setText("");
        campoPassword.setText("");
        campoUsuario.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonCancelar) {
            ventana.mostrarMensaje("");
            ventana.abrirInicio();
        } else {
            crear();
        }
    }

    private void crear() {
        String username = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword());
        if (username.isEmpty()) {
            ventana.mostrarMensaje("Ingrese un username.");
            return;
        }
        try {
            ventana.getGestorUsuarios().crear(username, password);
            ventana.abrirInicio();
            ventana.mostrarMensaje("El jugador " + username + " fue creado. Ya puede iniciar sesión.");
        } catch (UsuarioDuplicadoException ex) {
            ventana.mostrarMensaje(ex.getMessage() + " Ingrese un username diferente.");
            campoUsuario.setText("");
            campoUsuario.requestFocus();
        } catch (PasswordInvalidoException ex) {
            ventana.mostrarMensaje(ex.getMessage());
            campoPassword.setText("");
            campoPassword.requestFocus();
        }
    }
}
