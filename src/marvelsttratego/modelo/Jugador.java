
package marvelsttratego.modelo;

import marvelsttratego.excepciones.CredencialesInvalidasException;
import marvelsttratego.excepciones.PasswordInvalidoException;


public class Jugador {
     public static final int LONGITUD_PASSWORD = 5;
    public static final int PUNTOS_INICIALES = 0;

    private final String username;     
    private String password;            
    private int puntos;
    private int partidasHeroes;
    private int partidasVillanos;

    public Jugador(String username, String password) throws PasswordInvalidoException {
        validarPassword(password);
        this.username = username;
        this.password = password;
        this.puntos = PUNTOS_INICIALES;
        this.partidasHeroes = 0;
        this.partidasVillanos = 0;
    }

    
    public static void validarPassword(String password) throws PasswordInvalidoException {
        if (password == null || password.length() != LONGITUD_PASSWORD) {
            throw new PasswordInvalidoException(
                    "El password debe tener exactamente " + LONGITUD_PASSWORD + " caracteres.");
        }
    }



    public String getUsername()      {
        return username; 
    }
    public int getPuntos()           { 
        return puntos; 
    }
    public int getPartidasHeroes()   { 
        return partidasHeroes; 
    }
    public int getPartidasVillanos() { 
        return partidasVillanos; 
    }
    public int getPartidasTotales()  { 
        return partidasHeroes + partidasVillanos;
    }

 

    
    public boolean passwordCoincide(String intento) {
        return password.equals(intento);
    }

   
    public void cambiarPassword(String actual, String nuevo)
            throws CredencialesInvalidasException, PasswordInvalidoException {
        if (!passwordCoincide(actual)) {
            throw new CredencialesInvalidasException("El password actual es incorrecto.");
        }
        validarPassword(nuevo);
        this.password = nuevo;
    }

    
    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

   
    public void registrarPartida(Bando bando) {
        switch (bando) {
            case HEROES:
                partidasHeroes++;
                break;
            case VILLANOS:
                partidasVillanos++;
                break;
        }
    }

    @Override
    public String toString() {
        return username + " (" + puntos + " pts)";
    }
}
