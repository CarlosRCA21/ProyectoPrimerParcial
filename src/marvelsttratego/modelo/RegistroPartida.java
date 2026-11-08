
package marvelsttratego.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroPartida {
    
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
     private final String usernameGanador;
     private final String usernamePerdedor;
     private final Bando bandoGanador;
     private final TipoFinPartida tipoFin;
     private final LocalDateTime fecha;

    public RegistroPartida(String usernameGanador, String usernamePerdedor, Bando bandoGanador, TipoFinPartida tipoFin, LocalDateTime fecha) {
        this.usernameGanador = usernameGanador;
        this.usernamePerdedor = usernamePerdedor;
        this.bandoGanador = bandoGanador;
        this.tipoFin = tipoFin;
        this.fecha = fecha;
    }

    public String getUsernameGanador() {
        return usernameGanador;
    }

    public String getUsernamePerdedor() {
        return usernamePerdedor;
    }

    public Bando getBandoGanador() {
        return bandoGanador;
    }
    public Bando getBandoPerdedor() {
        return bandoGanador.getOpuesto();
    }

    public TipoFinPartida getTipoFin() {
        return tipoFin;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
     
    public boolean involucra(String username){
        return usernameGanador.equals(username) || usernamePerdedor.equals(username);
        
    }
    
    public String getMensaje() {
        String ganador = usernameGanador + " usando " + bandoGanador.getNombre().toUpperCase();
        String perdedor = usernamePerdedor + " usando " + getBandoPerdedor().getNombre().toUpperCase();
        String fechaTexto = fecha.format(FORMATO_FECHA);

        switch (tipoFin) {
            case TIERRA_CAPTURADA:
                String accion = (bandoGanador == Bando.HEROES) ? "SALVADO" : "CAPTURADO";
                return ganador + " ha " + accion + " la TIERRA! Venciendo a "
                        + usernamePerdedor + " — " + fechaTexto;
            case SIN_MOVIMIENTOS:
                return perdedor + " ha perdido por no tener movimientos válidos disponibles ante "
                        + usernameGanador + " — " + fechaTexto;
            case RETIRO:
                return ganador + " ha ganado ya que " + perdedor
                        + " se ha retirado del juego — " + fechaTexto;
            default:
                return ganador + " venció a " + usernamePerdedor + " — " + fechaTexto;
        }
    }
    
    @Override
    public String toString() {
        return getMensaje();
    } 
     
}
