
package marvelsttratego.excepciones;


public class MovimientoInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}