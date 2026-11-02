
package marvelsttratego.modelo;


public class Scout extends PiezaMovil {
    
    public static final int RANGO = 3;
    
    public Scout(String nombre, Bando bando){
        super(nombre, RANGO, bando);
    }
    
    @Override 
    public boolean puedeDesactivarBombas(){
        return true;
    }
}
