package marvelsttratego.vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

    private final BufferedImage fondo;

    public PanelFondo(String archivo) {
        fondo = GestorImagenes.cargar(archivo);
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 25, 60));
    }

    protected void agregarAbajo(JComponent componente) {
        GridBagConstraints posicion = new GridBagConstraints();
        posicion.weightx = 1;
        posicion.weighty = 1;
        posicion.anchor = GridBagConstraints.SOUTH;
        posicion.insets = new Insets(0, 0, 40, 0);
        add(componente, posicion);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo == null) {
            return;
        }
        double escalaAncho = (double) getWidth() / fondo.getWidth();
        double escalaAlto = (double) getHeight() / fondo.getHeight();
        double escala = Math.max(escalaAncho, escalaAlto);
        int ancho = (int) (fondo.getWidth() * escala);
        int alto = (int) (fondo.getHeight() * escala);
        int x = (getWidth() - ancho) / 2;
        int y = 0;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(fondo, x, y, ancho, alto, null);
    }
}
