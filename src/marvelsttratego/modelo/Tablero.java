
package marvelsttratego.modelo;

import marvelsttratego.excepciones.MovimientoInvalidoException;

public class Tablero {
     public static final int FILAS = 10;
    public static final int COLUMNAS = 10;

    
    private static final int ZONA_FILA_INICIO = 4;
    private static final int ZONA_FILA_FIN = 5;
    private static final int ZONA1_COL_INICIO = 2;
    private static final int ZONA1_COL_FIN = 3;
    private static final int ZONA2_COL_INICIO = 6;
    private static final int ZONA2_COL_FIN = 7;

    private final Pieza[][] celdas = new Pieza[FILAS][COLUMNAS];

   
    public boolean estaEnTablero(Posicion p) {
        return p.getFila() >= 0 && p.getFila() < FILAS
                && p.getColumna() >= 0 && p.getColumna() < COLUMNAS;
    }

    
    public boolean esZonaProhibida(Posicion p) {
        int f = p.getFila();
        int c = p.getColumna();
        boolean filaEnZona = f >= ZONA_FILA_INICIO && f <= ZONA_FILA_FIN;
        boolean colEnZona1 = c >= ZONA1_COL_INICIO && c <= ZONA1_COL_FIN;
        boolean colEnZona2 = c >= ZONA2_COL_INICIO && c <= ZONA2_COL_FIN;
        return filaEnZona && (colEnZona1 || colEnZona2);
    }

    
    public Pieza getPieza(Posicion p) {
        if (!estaEnTablero(p)) {
            return null;
        }
        return celdas[p.getFila()][p.getColumna()];
    }

   
    public boolean estaVacia(Posicion p) {
        return estaEnTablero(p) && !esZonaProhibida(p) && celdas[p.getFila()][p.getColumna()] == null;
    }

    
    public void colocar(Pieza pieza, Posicion destino) throws MovimientoInvalidoException {
        validarDestino(destino);
        celdas[destino.getFila()][destino.getColumna()] = pieza;
    }

    
    public Pieza quitar(Posicion p) {
        if (!estaEnTablero(p)) {
            return null;
        }
        Pieza quitada = celdas[p.getFila()][p.getColumna()];
        celdas[p.getFila()][p.getColumna()] = null;
        return quitada;
    }

    
    public void mover(Posicion origen, Posicion destino) throws MovimientoInvalidoException {
        Pieza pieza = getPieza(origen);
        if (pieza == null) {
            throw new MovimientoInvalidoException("No hay ninguna pieza en " + origen + ".");
        }
        validarDestino(destino);
        celdas[origen.getFila()][origen.getColumna()] = null;
        celdas[destino.getFila()][destino.getColumna()] = pieza;
    }

    private void validarDestino(Posicion destino) throws MovimientoInvalidoException {
        if (!estaEnTablero(destino)) {
            throw new MovimientoInvalidoException("La casilla " + destino + " esta fuera del tablero.");
        }
        if (esZonaProhibida(destino)) {
            throw new MovimientoInvalidoException("La casilla " + destino + " es una zona prohibida.");
        }
        if (celdas[destino.getFila()][destino.getColumna()] != null) {
            throw new MovimientoInvalidoException("La casilla " + destino + " ya esta ocupada.");
        }
    }
}
