package marvelsttratego.control;

import java.util.Random;
import marvelsttratego.excepciones.MovimientoInvalidoException;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Pieza;
import marvelsttratego.modelo.Posicion;
import marvelsttratego.modelo.Tablero;
import marvelsttratego.modelo.TipoPieza;

public class GeneradorFormacion {

    private static final int FILAS_BANDO = 4;

    private final Random azar = new Random();
    private final CatalogoPiezas catalogo = new CatalogoPiezas();

    private Tablero tablero;
    private int filaAtras;
    private int paso;
    private boolean[][] ocupado;
    private Pieza[] piezas;
    private boolean[] usada;

    public void colocar(Tablero tablero, Bando bando) throws MovimientoInvalidoException {
        this.tablero = tablero;
        this.piezas = catalogo.crearPiezas(bando);
        this.usada = new boolean[piezas.length];
        this.ocupado = new boolean[FILAS_BANDO][Tablero.COLUMNAS];
        if (bando == Bando.HEROES) {
            filaAtras = 9;
            paso = -1;
        } else {
            filaAtras = 0;
            paso = 1;
        }

        colocarTierraYBombasCercanas();
        colocarBombasRestantes();
        colocarMisticos();
        colocarPrimeraFila();
        colocarRestantes();
    }

    private void colocarTierraYBombasCercanas() throws MovimientoInvalidoException {
        int columna = 1 + azar.nextInt(Tablero.COLUMNAS - 2);
        int indiceTierra = buscarTipo(TipoPieza.TIERRA);
        poner(indiceTierra, 0, columna);

        int primeraBomba = buscarTipo(TipoPieza.BOMBA);
        poner(primeraBomba, 0, columna - 1);
        poner(primeraBomba + 1, 0, columna + 1);
        poner(primeraBomba + 2, 1, columna);
    }

    private void colocarBombasRestantes() throws MovimientoInvalidoException {
        for (int i = 0; i < piezas.length; i++) {
            if (piezas[i].getTipo() == TipoPieza.BOMBA && !usada[i]) {
                colocarAleatorio(i, 0, 1);
            }
        }
    }

    private void colocarMisticos() throws MovimientoInvalidoException {
        for (int i = 0; i < piezas.length; i++) {
            if (piezas[i].getTipo() == TipoPieza.NORMAL && piezas[i].getRango() == 2) {
                colocarAleatorio(i, 2, 3);
            }
        }
    }

    private void colocarPrimeraFila() throws MovimientoInvalidoException {
        for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
            if (!ocupado[3][columna]) {
                poner(elegirNoUsada(), 3, columna);
            }
        }
    }

    private void colocarRestantes() throws MovimientoInvalidoException {
        for (int k = 0; k < FILAS_BANDO; k++) {
            for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
                if (!ocupado[k][columna]) {
                    poner(elegirNoUsada(), k, columna);
                }
            }
        }
    }

    private void colocarAleatorio(int indicePieza, int kInicio, int kFin)
            throws MovimientoInvalidoException {
        int k;
        int columna;
        do {
            k = kInicio + azar.nextInt(kFin - kInicio + 1);
            columna = azar.nextInt(Tablero.COLUMNAS);
        } while (ocupado[k][columna]);
        poner(indicePieza, k, columna);
    }

    private int buscarTipo(TipoPieza tipo) {
        for (int i = 0; i < piezas.length; i++) {
            if (piezas[i].getTipo() == tipo) {
                return i;
            }
        }
        return -1;
    }

    private int elegirNoUsada() {
        int libres = 0;
        for (int i = 0; i < usada.length; i++) {
            if (!usada[i]) {
                libres++;
            }
        }
        int elegida = azar.nextInt(libres);
        for (int i = 0; i < usada.length; i++) {
            if (!usada[i]) {
                if (elegida == 0) {
                    return i;
                }
                elegida--;
            }
        }
        return -1;
    }

    private void poner(int indicePieza, int k, int columna) throws MovimientoInvalidoException {
        usada[indicePieza] = true;
        ocupado[k][columna] = true;
        tablero.colocar(piezas[indicePieza], new Posicion(filaAtras + paso * k, columna));
    }
}
