
package marvelsttratego.modelo;


public enum ModoJuego {
    TUTORIAL("Tutorial", true),
    CLASICO("Clásico", false);
    
    private final String nombre;
    private final boolean mostrarTodas;
    
    ModoJuego(String nombre, boolean mostrarTodas){
    this.nombre = nombre;
    this.mostrarTodas = mostrarTodas;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public boolean mostrarTodasLasPiezas(){
        return mostrarTodas;
    }
    
}
