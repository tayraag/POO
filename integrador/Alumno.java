import java.util.*;
import javax.swing.*;

public class Alumno
{
   private String nombreCompleto;
   private Carrera carrera;
   private List<Materia> matAprobadas;
   private List<Materia>matCursadas;
   
   public Alumno(String nombreCompleto, Carrera carrera){
       try{this.nombreCompleto = nombreCompleto;
           this.carrera = carrera;
           this.matAprobadas = this.matAprobadas != null ? this.matAprobadas : new ArrayList<>(); 
           this.matCursadas = this.matCursadas != null ? this.matCursadas : new ArrayList<>();}
       catch(IllegalArgumentException e){System.out.println("Error en la creación de Alumno: " + e.getMessage());} 
       catch(Exception e){System.out.println("Se produjo un error inesperado al crear el Alumno: " + e.getMessage()); }
   }
   
   public String getNombreCompleto(){return nombreCompleto;}
   
   public void setCarrera(Carrera carrera){
        if(carrera == null){System.out.println("La carrera no puede ser nula.");}
        this.carrera = carrera;
   }
    
   public Carrera getCarrera(){return carrera;}
       
   public boolean inscripcionMateria(Materia mat, AdministradorInscripcion inscribe){
       return inscribe.inscribirAlumnoMateria(this, mat);
   }
   
   public List<Materia>getMateriasFaltantes(){
       List<Materia>faltantes = new ArrayList<>(carrera.getMateriasObligatorias());
       faltantes.removeAll(matAprobadas);
       return faltantes;
   }
   
   public List<Materia>getMateriaCursadas(){return matCursadas;}
   
   public List<Materia>getMateriasAprobadas(){
       List<Materia> materiasAprobadas = new ArrayList<>();
       for(Materia mat : matAprobadas){
            if(mat.materiaAprobadaTotal()){materiasAprobadas.add(mat);}}
       return materiasAprobadas;
   }
   
   public void aprobarMateria(Materia materia){if(!matAprobadas.contains(materia)){matAprobadas.add(materia);}}
   
   public boolean finalizoCarrera(){return carrera.finalizoCarrera(this);}
   
   @Override
    public String toString() {
        return "Alumno{"+"nombreCompleto='"+nombreCompleto +'\''+", carrera="+carrera+", materiasCursadas=" +getMateriaCursadas().toString()+", materiasAprobadas=" +getMateriasAprobadas().toString() +'}';
   }
}
