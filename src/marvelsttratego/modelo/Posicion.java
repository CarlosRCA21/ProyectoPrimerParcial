
package marvelsttratego.modelo;


public class Posicion {
    
    private final int fila;
    private final int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    public Posicion desplazar( int cantF, int cantC){
        return new Posicion(fila + cantF, columna + cantC);
    }
    
    public boolean esAdyacente (Posicion otra){
        int cantF,cantC;
        cantF=Math.abs(fila-otra.fila);
        cantC = Math.abs(columna - otra.columna);
        return cantF + cantC == 1;
    }
    
    public boolean enLineaRecta(Posicion otra){
        if(this.equals(otra)){
            return false;
        }
        return fila == otra.fila || columna == otra.columna;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if (obj == null || getClass () != obj.getClass()){
            return false;
        }
        Posicion otra = (Posicion) obj;
        return fila == otra.fila && columna == otra.columna;
    }
    
    @Override 
    public int hashCode(){
        return 31*fila +columna;
    }
    
    @Override
    public String toString(){
        return "("+fila+", "+ columna + ")";
    }
}
