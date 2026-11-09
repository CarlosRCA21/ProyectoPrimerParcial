package marvelsttratego.vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import marvelsttratego.modelo.Tablero;

public class PanelTablero extends JPanel {

    private final BufferedImage fondo = GestorImagenes.getTablero();
    private final Casilla[][] casillas = new Casilla[Tablero.FILAS][Tablero.COLUMNAS];
    private int celda = 0;
    private int inicioX = 0;
    private int inicioY = 0;

    public PanelTablero(ActionListener oyente) {
        setLayout(null);
        setBackground(new Color(10, 20, 45));
        for (int fila = 0; fila < Tablero.FILAS; fila++) {
            for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
                Casilla casilla = new Casilla(fila, columna);
                casilla.addActionListener(oyente);
                casillas[fila][columna] = casilla;
                add(casilla);
            }
        }
    }

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    @Override
    public void doLayout() {
        celda = Math.min(getWidth(), getHeight()) / Tablero.FILAS;
        inicioX = (getWidth() - celda * Tablero.COLUMNAS) / 2;
        inicioY = (getHeight() - celda * Tablero.FILAS) / 2;
        for (int fila = 0; fila < Tablero.FILAS; fila++) {
            for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
                casillas[fila][columna].setBounds(inicioX + columna * celda, inicioY + fila * celda, celda, celda);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(fondo, inicioX, inicioY, celda * Tablero.COLUMNAS, celda * Tablero.FILAS, null);
    }
}
