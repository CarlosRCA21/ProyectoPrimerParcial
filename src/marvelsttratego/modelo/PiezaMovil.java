
package marvelsttratego.modelo;


public class PiezaMovil extends Pieza {
    
    public PiezaMovil(String nombre, int rango, Bando bando){
        super(nombre, rango, bando, TipoPieza.NORMAL);
    }
    
    @Override
    public int getAlcance(){
        return 1;
    }
    
    public boolean ganaPorHabilidadContra(Pieza defensor){
        return false;
    }
    
    public boolean puedeDesactivarBombas(){
        return false;
    }
    
    @Override
    public ResultadoCombate recibirAtaque(PiezaMovil atacante){
        if(atacante.ganaPorHabilidadContra(this)){
            return ResultadoCombate.GANA_ATACANTE;
        }
        if (atacante.getRango() > this.getRango()) {
            return ResultadoCombate.GANA_ATACANTE;
        }
        
        if(atacante.getRango() < this.getRango()){
            return ResultadoCombate.GANA_DEFENSOR;
        }
        return ResultadoCombate.EMPATE;
    }
}
