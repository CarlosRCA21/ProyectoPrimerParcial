
package marvelsttratego.modelo;


public enum TipoPieza {
    NORMAL(true,true),
    BOMBA(false,false),
    TIERRA(false,false);
    
    private final boolean puedeMoverse;
    private final boolean puedeAtacar;
    
    TipoPieza(boolean puedeMoverse, boolean puedeAtacar){
        this.puedeMoverse = puedeMoverse;
        this.puedeAtacar = puedeAtacar;
    }
    
    public boolean puedeMoverse(){
        return puedeMoverse;
    }
    public boolean puedeAtacar(){
        return puedeMoverse;
    }
    
    
    
    
}
