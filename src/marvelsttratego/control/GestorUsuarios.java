package marvelsttratego.control;

import marvelsttratego.excepciones.CredencialesInvalidasException;
import marvelsttratego.excepciones.PasswordInvalidoException;
import marvelsttratego.excepciones.UsuarioDuplicadoException;
import marvelsttratego.modelo.Jugador;

public class GestorUsuarios {

    private Jugador[] usuarios = new Jugador[10];
    private int cantidad = 0;
    private int historicos = 0;

    public void crear(String username, String password)
            throws UsuarioDuplicadoException, PasswordInvalidoException {
        if (buscar(username) != null) {
            throw new UsuarioDuplicadoException("El username " + username + " ya existe.");
        }
        Jugador nuevo = new Jugador(username, password);
        if (cantidad == usuarios.length) {
            agrandar();
        }
        usuarios[cantidad] = nuevo;
        cantidad++;
        historicos++;
    }

    public Jugador buscar(String username) {
        for (int i = 0; i < cantidad; i++) {
            if (usuarios[i].getUsername().equalsIgnoreCase(username)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public Jugador login(String username, String password) throws CredencialesInvalidasException {
        Jugador jugador = buscar(username);
        if (jugador == null || !jugador.passwordCoincide(password)) {
            throw new CredencialesInvalidasException("Username o password incorrectos.");
        }
        return jugador;
    }

    public void eliminar(Jugador jugador, String password) throws CredencialesInvalidasException {
        if (!jugador.passwordCoincide(password)) {
            throw new CredencialesInvalidasException("El password es incorrecto.");
        }
        for (int i = 0; i < cantidad; i++) {
            if (usuarios[i] == jugador) {
                for (int j = i; j < cantidad - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[cantidad - 1] = null;
                cantidad--;
                return;
            }
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getHistoricos() {
        return historicos;
    }

    public Jugador getUsuario(int indice) {
        return usuarios[indice];
    }

    public Jugador[] getRanking() {
        Jugador[] ranking = new Jugador[cantidad];
        for (int i = 0; i < cantidad; i++) {
            ranking[i] = usuarios[i];
        }
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (ranking[j].getPuntos() < ranking[j + 1].getPuntos()) {
                    Jugador temporal = ranking[j];
                    ranking[j] = ranking[j + 1];
                    ranking[j + 1] = temporal;
                }
            }
        }
        return ranking;
    }

    private void agrandar() {
        Jugador[] nuevo = new Jugador[usuarios.length * 2];
        for (int i = 0; i < usuarios.length; i++) {
            nuevo[i] = usuarios[i];
        }
        usuarios = nuevo;
    }
}
