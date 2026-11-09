package marvelsttratego.control;

import marvelsttratego.excepciones.MovimientoInvalidoException;
import marvelsttratego.modelo.Atacable;
import marvelsttratego.modelo.Bando;
import marvelsttratego.modelo.Jugador;
import marvelsttratego.modelo.Movible;
import marvelsttratego.modelo.Partida;
import marvelsttratego.modelo.Pieza;
import marvelsttratego.modelo.PiezaMovil;
import marvelsttratego.modelo.Posicion;
import marvelsttratego.modelo.ResultadoCombate;
import marvelsttratego.modelo.Tablero;
import marvelsttratego.modelo.TipoFinPartida;
import marvelsttratego.modelo.TipoPieza;

public class MotorJuego {

    private static final int PUNTOS_VICTORIA = 3;

    private final Partida partida;
    private final GestorPartidas gestorPartidas;

    public MotorJuego(Partida partida, GestorPartidas gestorPartidas) {
        this.partida = partida;
        this.gestorPartidas = gestorPartidas;
    }

    public Partida getPartida() {
        return partida;
    }

    public void prepararTablero() throws MovimientoInvalidoException {
        GeneradorFormacion generador = new GeneradorFormacion();
        generador.colocar(partida.getTablero(), Bando.HEROES);
        generador.colocar(partida.getTablero(), Bando.VILLANOS);
    }

    public static String describir(Pieza pieza) {
        if (pieza.getTipo() == TipoPieza.BOMBA) {
            return pieza.getNombre() + " (bomba)";
        }
        if (pieza.getTipo() == TipoPieza.TIERRA) {
            return pieza.getNombre() + " (tierra)";
        }
        return pieza.getNombre() + " (rango " + pieza.getRango() + ")";
    }

    public String mover(Posicion origen, Posicion destino) throws MovimientoInvalidoException {
        if (partida.estaTerminada()) {
            throw new MovimientoInvalidoException("La partida ya terminó.");
        }
        Tablero tablero = partida.getTablero();
        Pieza pieza = tablero.getPieza(origen);
        if (pieza == null) {
            throw new MovimientoInvalidoException("No hay ninguna pieza en la casilla de origen.");
        }
        if (!pieza.verificarBando(partida.getTurno())) {
            throw new MovimientoInvalidoException("Esa pieza no es suya.");
        }
        Movible movible = pieza;
        if (!movible.puedeMoverse()) {
            throw new MovimientoInvalidoException("Las bombas y la tierra no se pueden mover.");
        }
        if (!tablero.estaEnTablero(destino)) {
            throw new MovimientoInvalidoException("El destino está fuera del tablero.");
        }
        if (tablero.esZonaProhibida(destino)) {
            throw new MovimientoInvalidoException("El destino es una zona prohibida.");
        }

        Pieza ocupante = tablero.getPieza(destino);
        String mensaje;
        if (ocupante == null) {
            mensaje = moverPieza(pieza, origen, destino);
        } else if (ocupante.verificarBando(pieza.getBando())) {
            throw new MovimientoInvalidoException("No puede moverse a una casilla con una pieza propia.");
        } else {
            mensaje = atacar(pieza, ocupante, origen, destino);
        }
        pasarTurno();
        return mensaje;
    }

    public void retirarse() {
        if (!partida.estaTerminada()) {
            finalizar(partida.getTurno().getOpuesto(), TipoFinPartida.RETIRO);
        }
    }

    private String moverPieza(Pieza pieza, Posicion origen, Posicion destino)
            throws MovimientoInvalidoException {
        int pasos = Math.abs(origen.getFila() - destino.getFila())
                + Math.abs(origen.getColumna() - destino.getColumna());

        if (pasos > 1) {
            if (pieza.getAlcance() == 1) {
                throw new MovimientoInvalidoException("Esta pieza solo puede moverse una casilla.");
            }
            if (!origen.enLineaRecta(destino)) {
                throw new MovimientoInvalidoException("Debe moverse en línea recta.");
            }
            int dirFila = Integer.signum(destino.getFila() - origen.getFila());
            int dirColumna = Integer.signum(destino.getColumna() - origen.getColumna());
            if (!caminoLibre(origen, destino, dirFila, dirColumna)) {
                throw new MovimientoInvalidoException("El camino no está despejado.");
            }
        }

        partida.getTablero().mover(origen, destino);
        String mensaje = partida.getTurno().getNombre() + " movió una pieza.";
        if (pasos > 1) {
            pieza.revelar();
            mensaje = mensaje + " Se movió varias casillas y quedó revelada: " + describir(pieza) + ".";
        }
        return mensaje;
    }

    private boolean caminoLibre(Posicion actual, Posicion destino, int dirFila, int dirColumna) {
        Posicion siguiente = actual.desplazar(dirFila, dirColumna);
        if (siguiente.equals(destino)) {
            return true;
        }
        if (!partida.getTablero().estaVacia(siguiente)) {
            return false;
        }
        return caminoLibre(siguiente, destino, dirFila, dirColumna);
    }

    private String atacar(Pieza atacante, Pieza defensor, Posicion origen, Posicion destino)
            throws MovimientoInvalidoException {
        if (!origen.esAdyacente(destino)) {
            throw new MovimientoInvalidoException("Solo se puede atacar una pieza que esté al lado.");
        }
        Tablero tablero = partida.getTablero();
        PiezaMovil movil = (PiezaMovil) atacante;
        Atacable objetivo = defensor;
        ResultadoCombate resultado = objetivo.recibirAtaque(movil);
        atacante.revelar();
        defensor.revelar();

        String mensaje = "Ataque: " + describir(atacante) + " contra " + describir(defensor) + ". ";
        if (resultado == ResultadoCombate.GANA_ATACANTE) {
            tablero.quitar(destino);
            partida.agregarVencida(defensor);
            tablero.mover(origen, destino);
            mensaje = mensaje + "Gana el atacante.";
            if (defensor.getTipo() == TipoPieza.TIERRA) {
                finalizar(atacante.getBando(), TipoFinPartida.TIERRA_CAPTURADA);
            }
        } else if (resultado == ResultadoCombate.GANA_DEFENSOR) {
            tablero.quitar(origen);
            partida.agregarVencida(atacante);
            mensaje = mensaje + "Gana el defensor.";
        } else {
            tablero.quitar(origen);
            tablero.quitar(destino);
            partida.agregarVencida(atacante);
            partida.agregarVencida(defensor);
            mensaje = mensaje + "Empate: ambas piezas son eliminadas.";
        }
        return mensaje;
    }

    private void pasarTurno() {
        if (partida.estaTerminada()) {
            return;
        }
        partida.cambiarTurno();
        Bando turno = partida.getTurno();
        if (!hayMovimientos(turno, 0)) {
            finalizar(turno.getOpuesto(), TipoFinPartida.SIN_MOVIMIENTOS);
        }
    }

    private boolean hayMovimientos(Bando bando, int indice) {
        if (indice >= Tablero.FILAS * Tablero.COLUMNAS) {
            return false;
        }
        Posicion posicion = new Posicion(indice / Tablero.COLUMNAS, indice % Tablero.COLUMNAS);
        Pieza pieza = partida.getTablero().getPieza(posicion);
        if (pieza != null && pieza.verificarBando(bando) && pieza.puedeMoverse()
                && tieneCasillaAccesible(posicion, bando)) {
            return true;
        }
        return hayMovimientos(bando, indice + 1);
    }

    private boolean tieneCasillaAccesible(Posicion posicion, Bando bando) {
        int[] cambiosFila = {-1, 1, 0, 0};
        int[] cambiosColumna = {0, 0, -1, 1};
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < 4; i++) {
            Posicion vecina = posicion.desplazar(cambiosFila[i], cambiosColumna[i]);
            if (tablero.estaEnTablero(vecina) && !tablero.esZonaProhibida(vecina)) {
                Pieza ocupante = tablero.getPieza(vecina);
                if (ocupante == null || !ocupante.verificarBando(bando)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void finalizar(Bando ganador, TipoFinPartida tipo) {
        partida.terminar(ganador, tipo);
        Jugador jugadorGanador = partida.getGanador();
        Jugador jugadorPerdedor = partida.getPerdedor();
        jugadorGanador.sumarPuntos(PUNTOS_VICTORIA);
        jugadorGanador.registrarPartida(ganador);
        jugadorPerdedor.registrarPartida(ganador.getOpuesto());
        gestorPartidas.agregar(partida.generarRegistro());
    }
}
