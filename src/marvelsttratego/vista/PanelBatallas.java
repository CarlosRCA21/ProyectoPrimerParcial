package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import marvelsttratego.control.GestorPartidas;
import marvelsttratego.modelo.Bando;

public class PanelBatallas extends JPanel implements Actualizable {

    private final VentanaPrincipal ventana;
    private final JTextArea area = new JTextArea();

    public PanelBatallas(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        area.setEditable(false);
        area.setMargin(new Insets(15, 15, 15, 15));
        area.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    @Override
    public void actualizar() {
        GestorPartidas partidas = ventana.getGestorPartidas();
        String texto = "Usuarios activos: " + ventana.getGestorUsuarios().getCantidad() + "\n"
                + "Usuarios históricos (incluye eliminados): " + ventana.getGestorUsuarios().getHistoricos() + "\n"
                + "Partidas jugadas: " + partidas.getCantidad() + "\n"
                + "   Veces que ganaron los HÉROES: " + partidas.contarVictorias(Bando.HEROES) + "\n"
                + "   Veces que ganaron los VILLANOS: " + partidas.contarVictorias(Bando.VILLANOS) + "\n";
        area.setText(texto);
        area.setCaretPosition(0);
    }
}
