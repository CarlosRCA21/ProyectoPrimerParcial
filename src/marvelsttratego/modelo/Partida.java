
package marvelsttratego.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Partida {

    private final Tablero tablero;
    private final Jugador jugadorHeroes;
    private final Jugador jugadorVillanos;
    private final ModoJuego modo;

    private Bando turno;                             
    private final List<Pieza> vencidasHeroes = new ArrayList<>();
    private final List<Pieza> vencidasVillanos = new ArrayList<>();

    private boolean terminada;
    private Bando bandoGanador;                       
    private TipoFinPartida tipoFin;                   
    private LocalDateTime fechaFin;                   

    public Partida(Jugador jugadorHeroes, Jugador jugadorVillanos, ModoJuego modo) {
        if (jugadorHeroes == null || jugadorVillanos == null) {
            throw new IllegalArgumentException("La partida necesita dos jugadores.");
        }
        if (jugadorHeroes == jugadorVillanos
                || jugadorHeroes.getUsername().equals(jugadorVillanos.getUsername())) {
            throw new IllegalArgumentException("Los dos jugadores deben ser distintos.");
        }
        this.tablero = new Tablero();
        this.jugadorHeroes = jugadorHeroes;
        this.jugadorVillanos = jugadorVillanos;
        this.modo = modo;
        this.turno = Bando.HEROES;
        this.terminada = false;
    }

    

    public Tablero getTablero() { return tablero; }
    public ModoJuego getModo()  { return modo; }
    public Bando getTurno()     { return turno; }

  
    public Jugador getJugador(Bando bando) {
        if (bando == Bando.HEROES) {
            return jugadorHeroes;
        }
        return jugadorVillanos;
    }

    public Jugador getJugadorEnTurno() {
        return getJugador(turno);
    }
    
  
    public void cambiarTurno() {
        turno = turno.getOpuesto();
    }

    

  
    public void agregarVencida(Pieza pieza) {
        pieza.revelar();
        if (pieza.getBando() == Bando.HEROES) {
            vencidasHeroes.add(pieza);
        } else {
            vencidasVillanos.add(pieza);
        }
    }

    
    public List<Pieza> getVencidas(Bando bando) {
        if (bando == Bando.HEROES) {
            return Collections.unmodifiableList(vencidasHeroes);
        }
        return Collections.unmodifiableList(vencidasVillanos);
    }

    

    public boolean estaTerminada() {
        return terminada; 
    }

    
    public void terminar(Bando ganador, TipoFinPartida tipo) {
        if (terminada) {
            throw new IllegalStateException("La partida ya había terminado.");
        }
        this.terminada = true;
        this.bandoGanador = ganador;
        this.tipoFin = tipo;
        this.fechaFin = LocalDateTime.now();
    }

    public Bando getBandoGanador()   { 
        return bandoGanador; 
    }
    public TipoFinPartida getTipoFin() {
        return tipoFin;
    }

    public Jugador getGanador() {
        if (terminada) {
            return getJugador(bandoGanador);
        }
        return null;
    }

    public Jugador getPerdedor() {
        if (terminada) {
            return getJugador(bandoGanador.getOpuesto());
        }
        return null;
    }

    
    public RegistroPartida generarRegistro() {
        if (!terminada) {
            throw new IllegalStateException("La partida aún no ha terminado.");
        }
        return new RegistroPartida(getGanador().getUsername(), getPerdedor().getUsername(),
                bandoGanador, tipoFin, fechaFin);
    }
}
