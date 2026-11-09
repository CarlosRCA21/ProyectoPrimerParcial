package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import marvelsttratego.modelo.Jugador;

public class PanelRanking extends JPanel implements Actualizable {

    private final VentanaPrincipal ventana;
    private final JTextArea area = new JTextArea();

    public PanelRanking(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        area.setEditable(false);
        area.setMargin(new Insets(15, 15, 15, 15));
        area.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    @Override
    public void actualizar() {
        Jugador[] ranking = ventana.getGestorUsuarios().getRanking();
        String texto = "POSICIÓN — USERNAME — PUNTOS\n\n";
        for (int i = 0; i < ranking.length; i++) {
            texto = texto + (i + 1) + " — " + ranking[i].getUsername() + " — " + ranking[i].getPuntos() + "\n";
        }
        area.setText(texto);
        area.setCaretPosition(0);
    }
}
