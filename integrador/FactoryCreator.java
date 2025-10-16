import java.util.*;
import javax.swing.*;

public class FactoryCreator{
    
    public Carrera creaCarrera(String nombre, int cantCuatrimestres, int cantOptativasMinimas, PlanDeEstudio plan){
        return new Carrera(nombre, cantCuatrimestres, cantOptativasMinimas, plan);
    }
  
    public Materia creaMateria(String nombre, int cuatrimestre, boolean obligatoria, List<Materia> correlativas, Carrera carr){
        return new Materia(nombre, cuatrimestre, obligatoria, correlativas, carr);
    }
    
    public Alumno creaAlumno(String nombreCompleto, Carrera carrera){
        return new Alumno(nombreCompleto, carrera);
    }
    
    public void creaCorrelativa(Materia matP, Materia correlativa){
        try{
            if (matP == null || correlativa == null){System.out.println("La materia principal o la correlativa no pueden ser nulas.");}
            if (matP.equals(correlativa)){System.out.println("Una materia no puede ser correlativa de sí misma.");}
            if (correlativa.getCorrelativas().contains(matP)){System.out.println("No se puede agregar porque genera un ciclo.");}
            matP.agregaCorrelativa(correlativa);
            System.out.println("Correlativa agregada exitosamente: "+correlativa.getNombre()+" -> "+matP.getNombre());}   
        catch (IllegalArgumentException e){System.out.println("Error al agregar correlativa: " + e.getMessage());} 
        catch (Exception e){System.out.println("Se produjo un error inesperado: " + e.getMessage());}
    }
}