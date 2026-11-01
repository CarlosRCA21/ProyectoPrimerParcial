
package marvelsttratego.modelo;


public enum TipoFinPartida {
    TIERRA_CAPTIRADA("Captura de la tierra"),
    SIN_MOVIMIENTOS("Sin movimientos balidos"),
    RETIRO("Se retiro del juego");
    
    private final String descripcion;
    
    TipoFinPartida(String descripcion){
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
}
