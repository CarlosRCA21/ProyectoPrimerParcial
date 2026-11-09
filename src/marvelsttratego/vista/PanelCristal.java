package marvelsttratego.vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelCristal extends JPanel {

    public PanelCristal() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 255, 220));
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
    }
}
