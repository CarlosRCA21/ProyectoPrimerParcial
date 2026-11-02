
package marvelsttratego.modelo;


public class PiezaTierra extends Pieza{
    
    public PiezaTierra(Bando bando){
        super("Tierra", 0, bando, TipoPieza.TIERRA);
    }
    
    @Override
    public int getAlcance(){
        return 0;
    }
    
    @Override 
    public ResultadoCombate recibirAtaque(PiezaMovil atacante){
        return ResultadoCombate.GANA_ATACANTE;
    }
}
