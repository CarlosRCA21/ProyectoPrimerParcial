
package marvelsttratego.modelo;


public enum Bando {
    HEROES("Heroes"),
    VILLANOS("Villanos");
    
    private final String nombre;
    
    Bando(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    public Bando getOpuesto(){
        if(this == HEROES){
            return VILLANOS;
        }else{
            return HEROES;
        }
    }
}
