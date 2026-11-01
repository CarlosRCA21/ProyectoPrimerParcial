
package marvelsttratego.modelo;


public abstract class Pieza {
    
    private final String nombre;
    private final int rango;
    private final Bando bando;
    private final TipoPieza tipo;
    private boolean revelada;
    
    protected Pieza(String nombre, int rango, Bando bando, TipoPieza tipo){
        this.nombre = nombre;
        this.rango = rango;
        this.bando = bando;
        this.tipo = tipo;
        this.revelada = false;
        
    }

    public String getNombre() {
        return nombre;
    }

    public int getRango() {
        return rango;
    }

    public Bando getBando() {
        return bando;
    }

    public TipoPieza getTipo() {
        return tipo;
    }
    
    public boolean estaRevelada(){
        return revelada;
    }
    
    public void revelar(){
        revelada = true;
    }
    
    public void ocultar(){
        revelada = false;
    }
    
    //Para saber si la pieza es mia o del enemigo
    public boolean verificarBando(Bando otro){
        return bando == otro;
    }
    
    public boolean puedeMoverse(){
        return tipo.puedeMoverse();
    }
    
    public boolean puedeAtacar(){
        return tipo.puedeAtacar();
    }
    
    public abstract int getAlcance();
    
    @Override
    public String toString(){
        return nombre + " (rango " +rango+ ", " + bando.getNombre() + ")";
    }
    
}
