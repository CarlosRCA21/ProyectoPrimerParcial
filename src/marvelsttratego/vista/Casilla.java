package marvelsttratego.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class Casilla extends JButton {

    private static final int MARGEN = 3;

    private final int fila;
    private final int columna;
    private BufferedImage imagen;
    private String textoRespaldo = "";
    private boolean seleccionada = false;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setMargin(new Insets(0, 0, 0, 0));
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void mostrar(BufferedImage imagen, String textoRespaldo, boolean seleccionada) {
        this.imagen = imagen;
        this.textoRespaldo = textoRespaldo;
        this.seleccionada = seleccionada;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int ancho = getWidth() - 2 * MARGEN;
        int alto = getHeight() - 2 * MARGEN;

        if (imagen != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(imagen, MARGEN, MARGEN, ancho, alto, null);
        } else if (textoRespaldo.length() > 0) {
            g2.setColor(new Color(40, 40, 70));
            g2.fillRect(MARGEN, MARGEN, ancho, alto);
            g2.setColor(Color.WHITE);
            g2.drawString(textoRespaldo, MARGEN + 6, getHeight() / 2);
        }

        if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 70));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        if (seleccionada) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
        }
    }
}
