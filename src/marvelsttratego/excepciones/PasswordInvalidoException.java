
package marvelsttratego.excepciones;

public class PasswordInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasswordInvalidoException(String mensaje) {
        super(mensaje);
    }
}