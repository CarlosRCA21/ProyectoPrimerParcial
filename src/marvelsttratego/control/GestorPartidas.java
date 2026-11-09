package marvelsttratego.control;

import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.RegistroPartida;

public class GestorPartidas {

    private RegistroPartida[] registros = new RegistroPartida[10];
    private int cantidad = 0;

    public void agregar(RegistroPartida registro) {
        if (cantidad == registros.length) {
            RegistroPartida[] nuevo = new RegistroPartida[registros.length * 2];
            for (int i = 0; i < registros.length; i++) {
                nuevo[i] = registros[i];
            }
            registros = nuevo;
        }
        registros[cantidad] = registro;
        cantidad++;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int contarVictorias(Bando bando) {
        return contarVictorias(bando, 0);
    }

    private int contarVictorias(Bando bando, int indice) {
        if (indice >= cantidad) {
            return 0;
        }
        int actual = 0;
        if (registros[indice].getBandoGanador() == bando) {
            actual = 1;
        }
        return actual + contarVictorias(bando, indice + 1);
    }

    public RegistroPartida[] getRegistrosDe(String username) {
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            if (registros[i].involucra(username)) {
                total++;
            }
        }
        RegistroPartida[] resultado = new RegistroPartida[total];
        int posicion = 0;
        for (int i = cantidad - 1; i >= 0; i--) {
            if (registros[i].involucra(username)) {
                resultado[posicion] = registros[i];
                posicion++;
            }
        }
        return resultado;
    }
}
