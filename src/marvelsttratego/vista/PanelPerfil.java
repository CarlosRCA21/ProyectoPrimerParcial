package marvelsttratego.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import marvelsttratego.modelo.Jugador;
import marvelsttratego.modelo.RegistroPartida;

public class PanelPerfil extends JPanel implements Actualizable {

    private final VentanaPrincipal ventana;
    private final JTextArea area = new JTextArea();

    public PanelPerfil(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        area.setEditable(false);
        area.setMargin(new Insets(15, 15, 15, 15));
        area.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    @Override
    public void actualizar() {
        Jugador jugador = ventana.getUsuarioActual();
        String texto = "Username: " + jugador.getUsername() + "\n"
                + "Puntos: " + jugador.getPuntos() + "\n"
                + "Partidas con Héroes: " + jugador.getPartidasHeroes() + "\n"
                + "Partidas con Villanos: " + jugador.getPartidasVillanos() + "\n\n"
                + "Mis últimos juegos (del más reciente al más viejo):\n";

        RegistroPartida[] registros = ventana.getGestorPartidas().getRegistrosDe(jugador.getUsername());
        if (registros.length == 0) {
            texto = texto + "Aún no ha jugado ninguna partida.";
        }
        for (int i = 0; i < registros.length; i++) {
            texto = texto + (i + 1) + ". " + registros[i].getMensaje() + "\n";
        }
        area.setText(texto);
        area.setCaretPosition(0);
    }
}
