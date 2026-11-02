
package marvelsttratego.modelo;


public class Agente extends PiezaMovil {
    
    public static final int RANGO = 1;
    private static final int RANGO_VICTIMA = 10;
    
    public Agente(String nombre, Bando bando){
        super(nombre,RANGO,bando);
    }
    
    @Override
    public boolean ganaPorHabilidadContra(Pieza defensor){
        return defensor.getTipo() == TipoPieza.NORMAL && defensor.getRango() == RANGO_VICTIMA;
    }
}
