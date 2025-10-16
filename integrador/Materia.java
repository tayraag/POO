import java.util.*;
import javax.swing.*;

public class Materia {
    
    private String nombre;
    private int cuatrimestre;
    private boolean obligatoria;
    private List<Materia> correlativas;
    private boolean parcial;
    private boolean cursadaAprobada;
    private boolean finalAprobado;
    private boolean promocionada;
    private Carrera carr;
    
    public Materia(String nombre, int cuatrimestre, boolean obligatoria, List<Materia>correlativas, Carrera carr){
        this.nombre = nombre;
        this.cuatrimestre = cuatrimestre;
        this.obligatoria = obligatoria;
        this.correlativas = new ArrayList<>();
        this.parcial = parcial;
        this.cursadaAprobada = false;
        this.finalAprobado = false;
        this.promocionada = false;
        this.carr = carr;
    }

    public String getNombre(){return nombre;}
    
    public void agregaCorrelativa(Materia mat){if(!correlativas.contains(mat)){correlativas.add(mat);}}

    public int getCuatrimestre(){return cuatrimestre;}
    
    public boolean esObligatoria(){return obligatoria;}
    
    public List<Materia> getCorrelativas(){return correlativas;}
    
    public boolean cursadaAprobada(){return cursadaAprobada;} 
    
    public void apruebaCursada(boolean parcial){
        if(parcial){this.cursadaAprobada = true;}
        else{this.cursadaAprobada = false;}} 
    
    public boolean finalAprobado(){return finalAprobado;} 
    
    public void apruebaFinal(boolean finalA){
        if(cursadaAprobada && finalA){this.finalAprobado = true;}} 
    
    public boolean promocionAprobada(){return promocionada;} 
    
    public void apruebaPromocion(boolean promocion){
        if(cursadaAprobada && promocion){
            this.promocionada = true; 
            this.finalAprobado = true;}
            else{this.promocionada = false;
                 this.finalAprobado = false;}}
    
    public boolean materiaAprobadaTotal(){return finalAprobado || promocionada;}
    
    @Override
    public String toString() {
        return "Materia{"+"nombre="+nombre +'\''+", cuatrimestre="+cuatrimestre +", obligatoria="+obligatoria+ 
            ", correlativas="+correlativas.stream().map(Materia::getNombre).toList()+
               ", cursadaAprobada=" +cursadaAprobada+", finalAprobado=" +finalAprobado+", promocionada="+promocionada+'}';
    }
}
