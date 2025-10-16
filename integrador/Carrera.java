import java.util.*;
import javax.swing.*;

public class Carrera {
    
    private String nombreCarrera;
    private int cantCuatrimestres;
    private List<Materia> materiasObligatorias;
    private List<Materia> materiasOptativas;
    private List<Materia> materias;
    private int cantOptativasMinimas;
    private PlanDeEstudio plan;
    
    public Carrera(String nombreCarrera, int cantCuatrimestres, int cantOptativasMinimas, PlanDeEstudio plan){
        try{this.nombreCarrera = nombreCarrera;
            this.cantCuatrimestres = cantCuatrimestres;
            this.materiasObligatorias = materiasObligatorias != null ? materiasObligatorias : new ArrayList<>();
            this.materiasOptativas = materiasOptativas != null ? materiasOptativas : new ArrayList<>();
            this.materias = materias != null ? materias : new ArrayList<>();
            this.cantOptativasMinimas = cantOptativasMinimas;
            this.plan = plan;
        }catch(IllegalArgumentException e){System.out.println("Hubo un error al registrar la carrera: "+e.getMessage());}
    }
    
    public void setNombreCarrera(String nombre){this.nombreCarrera = nombre;}

    public void setPlanDeEstudio(PlanDeEstudio plan){this.plan = plan;}

    public PlanDeEstudio getPLanDeEstudio(){return plan;}
    
    public List<Materia>getMateriasObligatorias(){return materiasObligatorias;}
    
    public List<Materia>getMateriasOptativas(){return materiasOptativas;}
       
    public List<Materia>getMaterias(){return materias;}
    
    public String getNombreCarrera(){return nombreCarrera;}
    
    public void agregaMateria(Materia mat){
        try{if(mat == null){System.out.println("La materia no puede ser nula.");}
            if(mat.esObligatoria()){
                if(!materiasObligatorias.contains(mat)){materiasObligatorias.add(mat);materias.add(mat);} 
                    else{System.out.println("La materia ya está en la lista de materias obligatorias.");}}
                else{if(!materiasOptativas.contains(mat)){materiasOptativas.add(mat);materias.add(mat);} 
                        else{System.out.println("La materia ya está en la lista de materias optativas.");}}}
        catch(IllegalArgumentException e){System.out.println("No se pudo agregar la materia: "+e.getMessage());}
        catch(Exception e){System.out.println("Se produjo un error inesperado: " + e.getMessage());}
    }

    public boolean finalizoCarrera(Alumno alum){
        try{for(Materia obligatoria : materiasObligatorias){
                if(!alum.getMateriasAprobadas().contains(obligatoria)){return false;}}
            int optativasAprobadas = 0;
            for(Materia optativa : materiasOptativas){
                if(alum.getMateriasAprobadas().contains(optativa)){optativasAprobadas++;}}
                if(optativasAprobadas < cantOptativasMinimas){return false;}
            else{return true;}
        }catch(Exception e){
            System.out.println("Se produjo un error al verificar la finalización de la carrera: " + e.getMessage());
            return false;}
    }
    
    @Override
    public String toString(){
        return "Carrera{"+"nombreCarrera='"+nombreCarrera+'\''+", materiasObligatorias="+materiasObligatorias+
            ", materiasOptativas="+materiasOptativas+"cantOptativasMinimas="+cantOptativasMinimas+
           ", tipoPlan="+plan+'}';
    }
}
