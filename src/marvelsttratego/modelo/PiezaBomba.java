
package marvelsttratego.modelo;


public class PiezaBomba extends Pieza {
    
    public PiezaBomba(String nombre, Bando bando){
        super(nombre, 0, bando, TipoPieza.BOMBA);
    }
    
    @Override
    public int getAlcance(){
        return 0;
    }
    
    @Override 
    public ResultadoCombate recibirAtaque(PiezaMovil atacante){
        if(atacante.puedeDesactivarBombas()){
            return ResultadoCombate.GANA_ATACANTE;
        }
        return ResultadoCombate.GANA_DEFENSOR;
    }
}
