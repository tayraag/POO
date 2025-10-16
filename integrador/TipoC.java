import java.util.*;

public class TipoC implements TipoPlan
{
   @Override
   public boolean validaPlan(Alumno alum, Materia mat){
       List<Materia>correlativas = mat.getCorrelativas();
       List<Materia>aprobadasAlumno = alum.getMateriasAprobadas();
       for(Materia mate : correlativas){
            if(!aprobadasAlumno.contains(mate)||!mate.cursadaAprobada()){
                System.out.println("Correlativa no aprobada: " + mate.getNombre());return false;}}
       Carrera carrAlum = alum.getCarrera();
       int cuatriActual = mat.getCuatrimestre();
       List<Materia>materiasDeLaCarrera = new ArrayList<>();
       materiasDeLaCarrera.addAll(carrAlum.getMateriasObligatorias()); 
       materiasDeLaCarrera.addAll(carrAlum.getMateriasOptativas());
       for(Materia materia : materiasDeLaCarrera){
           int cuatriMateria = materia.getCuatrimestre();
           if(cuatriMateria >= cuatriActual - 5 && cuatriMateria < cuatriActual){
               if(!aprobadasAlumno.contains(materia)||!materia.materiaAprobadaTotal()){
                System.out.println("Materia no aprobada o cursada no aprobada: " +mat.getNombre());return false;}}}
       System.out.println("El alumno cumple con los requisitos para la materia: " + mat.getNombre());
       return true;
    }
}