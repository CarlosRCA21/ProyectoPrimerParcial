package marvelsttratego.vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class PanelInicio extends PanelFondo implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JButton botonLogin = new JButton("LOG IN");
    private final JButton botonCrear = new JButton("CREAR PLAYER");

    public PanelInicio(VentanaPrincipal ventana) {
        super("fondo_login.jpg");
        this.ventana = ventana;

        botonLogin.setPreferredSize(new Dimension(260, 60));
        botonCrear.setPreferredSize(new Dimension(260, 60));

        PanelCristal cristal = new PanelCristal();
        cristal.setLayout(new GridLayout(1, 2, 30, 0));
        cristal.add(botonLogin);
        cristal.add(botonCrear);
        agregarAbajo(cristal);

        botonLogin.addActionListener(this);
        botonCrear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ventana.mostrarMensaje("");
        if (e.getSource() == botonLogin) {
            ventana.abrirLogin();
        } else if (e.getSource() == botonCrear) {
            ventana.abrirCrear();
        }
    }
}
