package marvelsttratego;

import java.awt.Font;
import javax.swing.UIManager;
import marvelsttratego.control.GestorPartidas;
import marvelsttratego.control.GestorUsuarios;
import marvelsttratego.vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        ajustarFuentes();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorPartidas gestorPartidas = new GestorPartidas();
        VentanaPrincipal ventana = new VentanaPrincipal(gestorUsuarios, gestorPartidas);
        ventana.setVisible(true);
    }

    public static void ajustarFuentes() {
        Font normal = new Font("SansSerif", Font.PLAIN, 16);
        Font negrita = new Font("SansSerif", Font.BOLD, 16);
        UIManager.put("Label.font", normal);
        UIManager.put("Button.font", negrita);
        UIManager.put("TextField.font", normal);
        UIManager.put("PasswordField.font", normal);
        UIManager.put("TextArea.font", normal);
        UIManager.put("ComboBox.font", normal);
    }
}
