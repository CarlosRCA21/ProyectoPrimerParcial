package marvelsttratego.vista;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Pieza;

public class GestorImagenes {

    private static final String CARPETA = "/marvelsttratego/recursos/";
    private static final int MAXIMO = 100;

    private static String[] archivos = new String[MAXIMO];
    private static BufferedImage[] imagenes = new BufferedImage[MAXIMO];
    private static int cantidad = 0;

    public static BufferedImage cargar(String archivo) {
        for (int i = 0; i < cantidad; i++) {
            if (archivos[i].equals(archivo)) {
                return imagenes[i];
            }
        }
        BufferedImage imagen = leer(archivo);
        if (cantidad < MAXIMO) {
            archivos[cantidad] = archivo;
            imagenes[cantidad] = imagen;
            cantidad++;
        }
        return imagen;
    }

    private static BufferedImage leer(String archivo) {
        URL url = GestorImagenes.class.getResource(CARPETA + archivo);
        if (url == null) {
            return null;
        }
        try {
            return ImageIO.read(url);
        } catch (IOException ex) {
            return null;
        }
    }

    public static BufferedImage getFondoLogin() {
        return cargar("fondo_login.jpg");
    }

    public static BufferedImage getTablero() {
        return cargar("tablero.jpg");
    }

    public static BufferedImage getReverso(Bando bando) {
        return cargar(carpeta(bando) + "reverso.jpg");
    }

    public static BufferedImage getImagenPieza(Pieza pieza) {
        return cargar(carpeta(pieza.getBando()) + nombreArchivo(pieza.getNombre()) + ".jpg");
    }

    private static String carpeta(Bando bando) {
        if (bando == Bando.HEROES) {
            return "heroes/";
        }
        return "villanos/";
    }

    private static String nombreArchivo(String nombre) {
        String texto = nombre.toLowerCase();
        texto = texto.replace(".", "");
        texto = texto.replace("-", "_");
        texto = texto.replace(" ", "_");
        return texto;
    }
}
