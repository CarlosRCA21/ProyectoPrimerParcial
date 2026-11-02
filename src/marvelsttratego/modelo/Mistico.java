
package marvelsttratego.modelo;


public class Mistico extends PiezaMovil {
    
    public static final int RANGO = 2;
    private static final int ALCANCE_MAX = 9;
    
    public Mistico(String nombre, Bando bando){
        super(nombre,RANGO,bando);
    }
    
    @Override
    public int getAlcance(){
        return ALCANCE_MAX;
    }
}
